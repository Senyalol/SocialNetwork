import React, { useState } from "react";
import { Link, useParams } from "react-router-dom"; // Импортируем Link
import "./start.css"; // Подключение CSS

function StartPage() {
  const [menuOpen, setMenuOpen] = useState(false);
  const { id } = useParams();

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  // Пример новостей с отправителем
  const newsItems = [
    {
      id: 1,
      title: "В городе открывается музей 'Необычных шляп'",
      text: "В маленьком городке Шляпоград открылся первый в мире музей 'Необычных шляп'. В экспозиции представлены шляпы всех форм и размеров, включая шляпу в форме ананаса и 'шляпу-самолет'. Музей надеется привлечь туристов по всему миру, обещая уникальный опыт и возможность сделать селфи с 'шляпой-дельфином'.",
      sender: "Семен Науменко",
      image: "https://i.pinimg.com/originals/47/19/2d/47192d37e5beb7cf63fbf0d38ce46140.jpg"
    },
    {
      id: 2,
      title: "Пингвины стали модными и открыли свой фэшн-бренд",
      text: "В Антарктиде пингвины запустили свой собственный фэшн-бренд под названием 'Пингвин-Стиль'. Дизайнеры-пингвины представили коллекцию теплых курток и шарфов, вдохновленных их естественной средой обитания. 'Мы хотим, чтобы люди чувствовали себя также стильно, как мы!' — заявила модель-пингвин, позируя в новой коллекции.",
      sender: "Иван",
      image: "https://u.livelib.ru/reader/Annushka74/o/4wt0u0jz/o-o.jpeg"
    }
  ];

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
        <button className="menu-item" onClick={toggleMenu}>Закрыть</button>
        <Link to={`/users/${id}`} className="menu-item" onClick={toggleMenu}>Люди</Link> 
        <Link to={`/friends/${id}`} className="menu-item" onClick={toggleMenu}>Сообщения</Link>
        <Link to={`/profile/${id}`} className="menu-item" onClick={toggleMenu}>Профиль</Link>
      </div>

      {/* Main content */}
      <main className="main-content">
        <div className="news-container">
          <h2 className="news-title">Новости</h2>
          {newsItems.map(news => (
            <div key={news.id} className="news-item">
              <h3 className="news-subtitle">{news.title}</h3>
              <p className="news-text">{news.text}</p>
              <p className="news-sender">Отправитель: {news.sender}</p>
              <img 
                src={news.image} 
                className="news-image" 
                alt={news.title} 
              />
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}

export default StartPage;