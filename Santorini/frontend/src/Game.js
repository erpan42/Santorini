import React, { useState, useEffect } from 'react';
import './App.css';
import MessageDisplay from './MessageDisplay';
import Grid from './Grid';

const fetchWithRetry = async (url, options, retries = 3, backoff = 100) => {
  for (let attempt = 0; attempt <= retries; attempt++) {
    try {
      const response = await fetch(url, options);
      if (!response.ok) {
        // Attempt to parse the error message from the response
        const errorResponse = await response.json();
        throw new Error(errorResponse.error || `Request failed with status ${response.status}`);
      }
      return response; // If response is OK, return it directly
    } catch (error) {
      console.error(`Attempt ${attempt} failed: ${error.message}`);
      if (attempt < retries) {
        // If not on the last retry, wait for backoff period
        await new Promise(resolve => setTimeout(resolve, backoff * (2 ** attempt)));
      } else {
        // If on the last retry, rethrow the error to be caught by the caller
        throw error;
      }
    }
  }
};

const Player = ({ player }) => {
  return (
    <div className="player">
      <h3>{player.id}</h3>
      {player.workers.map((worker, index) => (
        <div key={index}>
          Worker {index + 1}: {worker.x !== null && worker.y !== null ? `(${worker.x}, ${worker.y})` : 'Not placed'}
        </div>
      ))}
    </div>
  );
};

const Game = () => {
  const [players, setPlayers] = useState([]);
  const [currentPlayer, setCurrentPlayer] = useState(null);
  const [winner, setWinner] = useState(null);
  const [selectedWorker, setSelectedWorker] = useState(null);
  const [grid, setGrid] = useState(Array(5).fill().map(() => Array(5).fill(null)));
  const [numPlacedWorkers, setNumPlacedWorkers] = useState(0);
  const [movedWorker, setMovedWorker] = useState(false);
  const [builtWorker, setBuiltWorker] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [messages, setMessages] = useState([]);


  useEffect(() => {
    const initializeGame = async () => {
      try {
        const response = await fetchWithRetry('http://localhost:8080/api/game/start', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ player1Id: 'Player 1', player2Id: 'Player 2' }),
        });
        const data = await response.json();
        console.log(data.grid); // Verify structure
        setPlayers(data.players);
        setCurrentPlayer(data.currentPlayer);
        setGrid(data.grid);
        setNumPlacedWorkers(data.numPlacedWorkers);
        // Reset error message upon successful request
        setErrorMessage('');
      } catch (error) {
        console.error('Error starting the game:', error);
        setErrorMessage(`Error starting the game: ${error.message}`);
      }
    };

    initializeGame();
  }, []);

  const handleWorkerSelection = async (row, col) => {
    try {
      const response = await fetchWithRetry(`http://localhost:8080/api/game/select-worker?row=${row}&col=${col}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const data = await response.json();
      setPlayers(data.players);
      setCurrentPlayer(data.currentPlayer);
      setGrid(data.grid);
      const selectedWorker = currentPlayer.workers.find(worker => worker.x === row && worker.y === col);
      setSelectedWorker(selectedWorker);
      setErrorMessage('');
      // Check for messages in the response and update the state
      if (data.messages && data.messages.length > 0) {
        setMessages(data.messages);
      }
    } catch (error) {
      console.error('Error selecting worker:', error);
      setErrorMessage(`Error selecting worker: ${error.message}`);
    }
  };
  
  const handleWorkerMovement = async (row, col) => {
    try {
      const response = await fetchWithRetry(`http://localhost:8080/api/game/move-worker?toRow=${row}&toCol=${col}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          playerId: currentPlayer.id,
        }),
      });
      const data = await response.json();
      setPlayers(data.players);
      setCurrentPlayer(data.currentPlayer);
      setWinner(data.winner);
      setGrid(data.grid);
      setErrorMessage('');
      // Check for messages in the response and update the state
      if (data.messages && data.messages.length > 0) {
        setMessages(data.messages);
        setMovedWorker(false); // Reset builtWorker after displaying messages (if any)
      } else {
        setMovedWorker(true);
      }
    } catch (error) {
      console.error('Error moving worker:', error);
      setErrorMessage(`Error moving worker: ${error.message}`);
    }
  };
  
  const handleWorkerBuilding = async (row, col) => {
    try {
      const response = await fetchWithRetry(`http://localhost:8080/api/game/build?row=${row}&col=${col}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          playerId: currentPlayer.id,
        }),
      });
      const data = await response.json();
      setPlayers(data.players);
      setCurrentPlayer(data.currentPlayer);
      setWinner(data.winner);
      setGrid(data.grid);
      setErrorMessage('');
      // Check for messages in the response and update the state
      if (data.messages && data.messages.length > 0) {
        setMessages(data.messages);
        setBuiltWorker(false);
      } else {
        setBuiltWorker(false);
        setSelectedWorker(null);
        setMovedWorker(false);
      }

    } catch (error) {
      console.error('Error building:', error);
      setErrorMessage(`Error building: ${error.message}`);
    }
  };
  
  const handleCellClick = async (row, col) => {
    if (!currentPlayer || winner) return;
  
    // Initial worker placement phase
    if (numPlacedWorkers < 4) {
      await placeWorker(row, col);
      return; // Early return to prevent other actions during placement phase
    }
  
    if (!selectedWorker) {
      await handleWorkerSelection(row, col);
    } else if (!movedWorker) {
      await handleWorkerMovement(row, col);
    } else if (!builtWorker) {
      await handleWorkerBuilding(row, col);
    }
  };

  // Function to place a worker
  const placeWorker = async (row, col) => {
    try {
      const response = await fetchWithRetry(`http://localhost:8080/api/game/place-worker?row=${row}&col=${col}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          playerId: currentPlayer.id,
        }),
      });
      const data = await response.json();
      setPlayers(data.players);
      setCurrentPlayer(data.currentPlayer);
      setGrid(data.grid);
      setNumPlacedWorkers(data.numPlacedWorkers); // Update the number of placed workers
      setErrorMessage('');
      // Check for messages in the response and display them
      // Check for messages in the response and update the state
      if (data.messages && data.messages.length > 0) {
        setMessages(data.messages);
      }
    } catch (error) {
      console.error('Error placing worker:', error);
      setErrorMessage(`Error starting the game: ${error.message}`);
    }
  };

  const handleNewGame = async () => {
    try {
      const response = await fetchWithRetry('http://localhost:8080/api/game/reset', {
        method: 'POST',
      });
      const data = await response.json();
      console.log('New game response:', data);
      setPlayers(data.players);
      setCurrentPlayer(data.currentPlayer);
      setGrid(data.grid);
      setWinner(null);
      setSelectedWorker(null);
      setNumPlacedWorkers(0);
      setErrorMessage('');
      setBuiltWorker(false);
      setMovedWorker(false);
      setMessages([]);
      setErrorMessage('');
    } catch (error) {
      console.error('Error resetting the game:', error);
      setErrorMessage(`Error starting the game: ${error.message}`);
    }
  };

  return (
    <div>
      <h1>Santorini</h1>
      <div className="game-info">
        <h2>Current Player: {currentPlayer?.id}</h2>
        {winner && <h2>Winner: {winner.id}</h2>}
        {errorMessage && <div className="error-message">{errorMessage}</div>}
      </div>
      <button>Skip Action</button>
      <button onClick={handleNewGame}>New Game</button>
      <Grid grid={grid} onCellClick={handleCellClick} />
      <div className="players">
        {players.map((player, index) => (
          <Player key={index} player={player} />
        ))}
      </div>
      <MessageDisplay messages={messages} />
    </div>
  );
};

export default Game;