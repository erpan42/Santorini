import React, { useState, useEffect } from 'react';
import './MessageDisplay.css';

const MessageDisplay = ({ messages }) => {
  const [displayMessages, setDisplayMessages] = useState([]);

  useEffect(() => {
    if (messages.length > 0) {
      setDisplayMessages(messages);
      const timer = setTimeout(() => {
        setDisplayMessages([]);
      }, 3000); // Clear messages after 5 seconds
      return () => clearTimeout(timer);
    }
  }, [messages]);

  return (
    <div className="message-display">
      {displayMessages.map((message, index) => (
        <div key={index} className="message-item">
          {message}
        </div>
      ))}
    </div>
  );
};

export default MessageDisplay;