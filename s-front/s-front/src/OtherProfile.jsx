import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import "./profile.css";

function OtherProfile() {
    const { id,friendId } = useParams();
    const [avatar, setAvatar] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [accountCreationDate] = useState("2023-01-01"); // Замените на актуальную дату
    const [posts, setPosts] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/users/${friendId}`);
                if (!response.ok) {
                    throw new Error("Ошибка при получении данных пользователя");
                }
                const userData = await response.json();
                setFirstName(userData.firstName);
                setLastName(userData.lastName);
                setUsername(userData.username);
                setEmail(userData.email);
                setImageUrl(userData.imageu);
                setAvatar(userData.imageu);

                // Fetch user posts
                const postsResponse = await fetch(`http://localhost:8080/api/posts?userId=${friendId}`);
                if (!postsResponse.ok) {
                    throw new Error("Ошибка при получении постов пользователя");
                }
                const userPosts = await postsResponse.json();
                setPosts(userPosts);
            } catch (error) {
                console.error(error);
                setError("Не удалось загрузить данные пользователя");
            }
        };

        fetchUserData();
    }, [id]);

    return (
        <div className="page-wrapper">
            <div className="profile-container">
                <div className="profile-header">
                    <div className="profile-info">
                        <h1>{username}</h1>
                        {imageUrl && <img src={imageUrl} alt="User Image" className="avatar" />}
                        <h2>{firstName}</h2>
                        <h2>{lastName}</h2>
                        <p>Электронная почта: {email}</p>
                        <p>Дата регистрации: {accountCreationDate}</p>
                    </div>
                </div>

                <div className="profile-content">
                    <div className="posts">
                        <h3>Посты пользователя</h3>
                        {posts.length > 0 ? (
                            posts.map((post, index) => (
                                <div key={index} className="post">
                                    {post.imageUrl && <img src={post.imageUrl} alt={`Post ${index} image`} className="post-image" />}
                                    <p>{post.text}</p>
                                    <p className="timestamp">{new Date(post.createdAt).toLocaleDateString()}</p>
                                </div>
                            ))
                        ) : (
                            <p>Постов пока нет.</p>
                        )}
                    </div>
                </div>

                <div className="navigation-buttons">
                    <Link to={`/start/${id}`} className="nav-button">Назад</Link>
                    <Link to="/" className="nav-button">Выйти из аккаунта</Link>
                </div>
            </div>

            {error && <div className="error-message">{error}</div>}
        </div>
    );
}

export default OtherProfile;