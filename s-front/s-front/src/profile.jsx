import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import "./profile.css";

function ProfilePage() {
    const { id } = useParams();
    const [avatar, setAvatar] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [accountCreationDate] = useState("2023-01-01");
    const [posts, setPosts] = useState([]);
    const [newPostText, setNewPostText] = useState("");
    const [newPostImage, setNewPostImage] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [newFirstName, setNewFirstName] = useState("");
    const [newLastName, setNewLastName] = useState("");
    const [newUsername, setNewUsername] = useState("");
    const [newEmail, setNewEmail] = useState("");
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/users/${id}`);
                if (!response.ok) {
                    throw new Error("Error fetching user data");
                }
                const userData = await response.json();
                setFirstName(userData.firstName);
                setLastName(userData.lastName);
                setUsername(userData.username);
                setEmail(userData.email);
                setImageUrl(userData.imageu);
                setAvatar(userData.imageu);
            } catch (error) {
                console.error(error);
                setError("Failed to load user data");
            }
        };

        const fetchUserPosts = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/posts?userId=${id}`);
                if (!response.ok) {
                    throw new Error("Error fetching user posts");
                }
                const postsData = await response.json();
                const formattedPosts = postsData.map(post => ({
                    text: post.text,
                    image: post.imageUrl,
                    timestamp: new Date(post.createdAt),
                }));
                setPosts(formattedPosts);
            } catch (error) {
                console.error(error);
                setError("Failed to load user posts");
            }
        };

        fetchUserData();
        fetchUserPosts();
    }, [id]);

    const handlePostImageChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setNewPostImage(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handlePostChange = (event) => {
        setNewPostText(event.target.value);
    };

    const addPost = async () => {
        if (newPostText.trim() !== "") {
            const newPost = {
                user_id: parseInt(id), // Assuming the user ID is required
                text: newPostText,
                imageUrl: newPostImage,
            };

            try {
                const response = await fetch(`http://localhost:8080/api/posts`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(newPost),
                });

                if (!response.ok) {
                    throw new Error("Error submitting post");
                }

                // Optionally fetch the updated posts list or add the new post locally
                setPosts([...posts, { ...newPost, timestamp: new Date() }]);
                setNewPostText("");
                setNewPostImage(null);
            } catch (error) {
                console.error(error);
                setError("Failed to submit post");
            }
        }
    };

    const handleEditClick = () => {
        setIsEditing(true);
        setNewUsername(username);
        setNewEmail(email);
        setNewFirstName(firstName);
        setNewLastName(lastName);
    };

    const handleSaveClick = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/users/${id}`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    username: newUsername,
                    email: newEmail,
                    firstName: newFirstName,
                    lastName: newLastName,
                    imageu: avatar,
                }),
            });

            if (!response.ok) {
                throw new Error("Error saving changes");
            }

            setUsername(newUsername);
            setEmail(newEmail);
            setFirstName(newFirstName);
            setLastName(newLastName);
            setImageUrl(avatar);
            setIsEditing(false);
        } catch (error) {
            console.error(error);
            setError("Failed to save changes");
        }
    };

    const handleCancelClick = () => {
        setIsEditing(false);
    };

    return (
        <div className="page-wrapper">
            <div className="profile-container">
                <div className="profile-header">
                    <div className="profile-info">
                        {isEditing ? (
                            <>
                                <div>
                                    <label htmlFor="username-edit">Username:</label>
                                    <input
                                        type="text"
                                        id="username-edit"
                                        value={newUsername}
                                        onChange={(e) => setNewUsername(e.target.value)}
                                    />
                                </div>
                                <div>
                                    <label htmlFor="email-edit">Email:</label>
                                    <input
                                        type="email"
                                        id="email-edit"
                                        value={newEmail}
                                        onChange={(e) => setNewEmail(e.target.value)}
                                    />
                                </div>
                                <div>
                                    <label htmlFor="firstName-edit">Имя:</label>
                                    <input
                                        type="text"
                                        id="firstName-edit"
                                        value={newFirstName}
                                        onChange={(e) => setNewFirstName(e.target.value)}
                                    />
                                </div>
                                <div>
                                    <label htmlFor="lastName-edit">Фамилия:</label>
                                    <input
                                        type="text"
                                        id="lastName-edit"
                                        value={newLastName}
                                        onChange={(e) => setNewLastName(e.target.value)}
                                    />
                                </div>
                                <div>
                                    <label htmlFor="avatar-edit">Аватар (URL):</label>
                                    <input
                                        type="text"
                                        id="avatar-edit"
                                        value={avatar}
                                        onChange={(e) => setAvatar(e.target.value)}
                                    />
                                </div>
                                <button onClick={handleSaveClick}>Сохранить изменения</button>
                                <button onClick={handleCancelClick}>Отмена</button>
                            </>
                        ) : (
                            <>
                                <h1>{username}</h1>
                                {imageUrl && <img src={imageUrl} alt="User Avatar" className="avatar" />}
                                <h2>{firstName}</h2>
                                <h2>{lastName}</h2>
                                <p>Электронная почта: {email}</p>
                                <p>Дата регистрации: {accountCreationDate}</p>
                                <button onClick={handleEditClick}>Редактировать данные аккаунта</button>
                            </>
                        )}
                    </div>
                </div>

                <div className="profile-content">
                    <div className="posts">
                        <h3>Мои посты</h3>
                        {posts.map((post, index) => (
                            <div key={index} className="post">
                                {post.image ? (
                                    <img src={post.image} alt={`Post ${index} image`} className="post-image" />
                                ) : null}
                                <p>{post.text}</p>
                                <p className="timestamp">{post.timestamp.toLocaleDateString()}</p>
                            </div>
                        ))}
                        <div className="new-post">
                            <textarea
                                value={newPostText}
                                onChange={handlePostChange}
                                placeholder="Напишите новый пост..."
                                className="post-input"
                            />
                            <input
                                type="file"
                                accept="image/*"
                                onChange={handlePostImageChange}
                                className="post-image-input"
                            />
                            <button onClick={addPost} className="post-button">
                                Опубликовать
                            </button>
                        </div>
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

export default ProfilePage;