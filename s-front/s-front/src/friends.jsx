import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import "./friends.css";

function FriendsPage() {
    const [friends, setFriends] = useState([]); // Начинаем с пустого массива
    const [userData, setUserData] = useState(null);
    const [newFriend, setNewFriend] = useState({ name: "", email: "", avatar: null });
    const [searchTerm, setSearchTerm] = useState("");
    const [showModal, setShowModal] = useState(false);
    const { id } = useParams(); // id - это user_id

    // Получение данных о пользователе
    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/users/${id}`);
                const data = await response.json();
                setUserData(data);
            } catch (error) {
                console.error("Ошибка при получении данных пользователя:", error);
            }
        };

        fetchUser();
    }, [id]);

    // Получение данных о друзьях
    useEffect(() => {
        const fetchFriends = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/friendships");
                const data = await response.json();

                // Фильтруем только тех друзей, которые относятся к текущему пользователю
                const friendsList = await Promise.all(data
                    .filter(friend => friend.user_id === parseInt(id)) // Сначала фильтруем по user_id
                    .map(async (friend) => {
                        const friendResponse = await fetch(`http://localhost:8080/api/users/${friend.friend_id}`);
                        const friendData = await friendResponse.json();

                        return {
                            userId: friend.user_id,
                            friendId: friend.friend_id,
                            name: friendData.username, // Используем username из данных о друге
                            email: friendData.email, // Используем email из данных о друге
                            avatar: friendData.imageu || null // Используем изображение из данных о друге
                        };
                    })
                );

                setFriends(friendsList);
            } catch (error) {
                console.error("Ошибка при получении друзей:", error);
            }
        };

        fetchFriends();
    }, [id]);

    const addFriend = () => {
        if (newFriend.name && newFriend.email) {
            setFriends([...friends, newFriend]);
            setNewFriend({ name: "", email: "", avatar: null });
            setShowModal(false);
        }
    };

    const removeFriend = (email) => {
        setFriends(friends.filter(friend => friend.email !== email));
    };

    const filteredFriends = friends.filter(friend =>
        friend.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        friend.email.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="friendsPage">
            <div className="friendsContainer">
                <Link to={`/start/${id}`} className="friendsBackButton">Назад</Link>
                <h1 className="friendsPageH1">Список Друзей</h1>
                <input
                    type="text"
                    placeholder="Поиск по имени или почте"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="friendsSearchInput"
                />
                <button className="friendsAddButton" onClick={() => setShowModal(true)}>Добавить друга</button>

                {showModal && (
                    <div className="friendsModal">
                        <div className="friendsModalContent">
                            <h2>Добавить Друга</h2>
                            <input
                                type="text"
                                placeholder="Имя"
                                value={newFriend.name}
                                onChange={(e) => setNewFriend({ ...newFriend, name: e.target.value })}
                                className="friendsModalInput"
                            />
                            <input
                                type="email"
                                placeholder="Почта"
                                value={newFriend.email}
                                onChange={(e) => setNewFriend({ ...newFriend, email: e.target.value })}
                                className="friendsModalInput"
                            />
                            <input
                                type="file"
                                accept="image/*"
                                onChange={(e) => {
                                    setNewFriend({ ...newFriend, avatar: URL.createObjectURL(e.target.files[0]) });
                                }}
                                className="friendsModalInput"
                            />
                            {newFriend.avatar && <img src={newFriend.avatar} alt="Avatar preview" className="friendsAvatarPreview" />}
                            {/* <button className="friendsModalButton" onClick={addFriend}>Добавить</button> */}
                            <button className="friendsModalButton" onClick={() => setShowModal(false)}>Закрыть</button>
                        </div>
                    </div>
                )}

                <div className="friendsList">
                    {filteredFriends.map(friend => (
                        <div key={friend.email} className="friendCard">
                            <img src={friend.avatar || 'default_avatar.png'} alt="Avatar" className="friendAvatar" />
                            <div className="friendInfo">
                                <p>Имя: {friend.name}</p>
                                <p>Почта: {friend.email}</p>
                            </div>
                            <div className="friendActions">
                                <Link to={`/chat/${id}/${friend.friendId}`} className="friendsMessageButton">Перейти в личные сообщения</Link>
                                {/* <Link to={`/profile/${encodeURIComponent(friend.name)}`} className="friendsProfileButton">Перейти в профиль</Link> */}
                                <button className="friendsRemoveButton" onClick={() => removeFriend(friend.email)}>Удалить</button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default FriendsPage;