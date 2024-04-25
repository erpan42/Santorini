import React from 'react';
import Cell from './Cell';

const Grid = ({ grid, onCellClick }) => {
  return (
    <div className="grid">
      {grid.map((row, rowIndex) => (
        row.map((cell, colIndex) => (
          <Cell
            key={`${rowIndex}-${colIndex}`}
            cell={cell}
            onCellClick={onCellClick}
            row={rowIndex}
            col={colIndex}
          />
        ))
      ))}
    </div>
  );
};

export default Grid;