import React from 'react';

const Cell = ({ cell, onCellClick, row, col }) => {
    // Check if cell is not null before destructuring
    if (!cell) {
      // console.log(`Cell (${row}, ${col}) is null`);
      return (
        <button className="cell" onClick={onCellClick}>
          &nbsp;
        </button>
      );
    }
  
    // Safely access the tower level with defaults
    const towerLevel = cell.towerLevel || 0;
    const workerPresent = cell.worker != null;
  
    // console.log(`Cell (${row}, ${col}):`, cell);
    // console.log(`Tower Level: ${towerLevel}`);
    // console.log(`Worker Present: ${workerPresent}`);
  
    let content = '';
  
    if (towerLevel > 0) {
      content = '['.repeat(towerLevel) + ']'.repeat(towerLevel);
  
      if (workerPresent) {
        const workerId = cell.worker.ownerId === 'Player 1' ? '1' : '2';
        content = '['.repeat(towerLevel) + workerId + ']'.repeat(towerLevel);
      }
  
      if (cell.hasDome) {
        content = '['.repeat(towerLevel) + '0' + ']'.repeat(towerLevel);
      }
    } else if (workerPresent) {
      const workerId = cell.worker.ownerId === 'Player 1' ? '1' : '2';
      content = workerId;
    }
  
    // console.log(`Rendered Content for (${row}, ${col}):`, content);
  
    return (
      <button className="cell" onClick={() => onCellClick(row, col)}>
        {content || <>&nbsp;</>}
      </button>
    );
};

  export default Cell;