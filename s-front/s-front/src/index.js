import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AutPage from './aut.jsx';
import RegPage from './reg.jsx';
import StartPage from './start.jsx';
import ProfilePage from './profile.jsx';
import FriendsPage from './friends.jsx';
import MessagesPage from './usersIn.jsx';
import ChatPage from './chat.jsx';
import OtherProfile from './OtherProfile.jsx';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
  <Routes>
  <Route path="/" element={<AutPage />}/>
  <Route path="/register" element={<RegPage/>}/>
  <Route path="/start/:id" element={<StartPage/>}/>
  <Route path="/profile/:id" element={<ProfilePage/>}/>
  <Route path="/friends/:id" element={<FriendsPage/>}/>
  <Route path="/users/:id" element={<MessagesPage/>}/>
  <Route path="/chat/:id/:friendId" element={<ChatPage />} />
  <Route path="/LookProf/:friendId/:id" element={<OtherProfile/>}/>
  </Routes>
  </BrowserRouter>
);


reportWebVitals();
