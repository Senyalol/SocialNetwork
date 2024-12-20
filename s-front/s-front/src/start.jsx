import React, { useState } from "react";
import { Link, useParams } from "react-router-dom"; // Импортируем Link
import "./start.css"; // Подключение CSS

function StartPage() {
  const [menuOpen, setMenuOpen] = useState(false);
  const {id} = useParams();

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  return (
    <div className="news-page">
      {/* Header */}
      <header className="start-header">
        <button className="menu-button" onClick={toggleMenu}>
          {menuOpen ? "✖️" : "☰"}
        </button>
        <h1 className="header-title">Community</h1>
      </header>

      {/* Menu */}
      <div className={`menu ${menuOpen ? "active" : ""}`}>
        <button className="menu-item" onClick={toggleMenu}>Закрыть</button> {/* Кнопка закрытия меню */}
        <Link to={`/users/${id}`} className="menu-item" onClick={toggleMenu}>Люди</Link> 
        <Link to={`/friends/${id}`} className="menu-item" onClick={toggleMenu}>Сообщения</Link>
        <Link to={`/profile/${id}`} className="menu-item" onClick={toggleMenu}>Профиль</Link>
      </div>

      {/* Main content */}
      <main className="main-content">
        <div className="news-container">
          <h2 className="news-title">Новости</h2>
          <p className="news-text">
            
          <img 
              src='https://i.pinimg.com/originals/47/19/2d/47192d37e5beb7cf63fbf0d38ce46140.jpg' 
              className="news-image" 
              alt="Необычные шляпы" 
            />

          1. В городе открывается музей "Необычных шляп"
             В маленьком городке Шляпоград открылся первый в мире музей "Необычных шляп". 
             В экспозиции представлены шляпы всех форм и размеров, включая шляпу в форме ананаса и "шляпу-самолет". 
             Музей надеется привлечь туристов по всему миру, обещая уникальный опыт и возможность сделать селфи с "шляпой-дельфином".
        
          <img 
              src='https://u.livelib.ru/reader/Annushka74/o/4wt0u0jz/o-o.jpeg' 
              className="news-image" 
              alt="Необычные шляпы" 
            />

          2. Пингвины стали модными и открыли свой фэшн-бренд
             В Антарктиде пингвины запустили свой собственный фэшн-бренд под названием "Пингвин-Стиль". 
             Дизайнеры-пингвины представили коллекцию теплых курток и шарфов, вдохновленных их естественной средой обитания. 
             "Мы хотим, чтобы люди чувствовали себя также стильно, как мы!" — заявила модель-пингвин, позируя в новой коллекции.
            

          </p>
        </div>
      </main>
    </div>
  );
}

export default StartPage;
