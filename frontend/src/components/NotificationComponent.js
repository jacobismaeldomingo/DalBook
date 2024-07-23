import React, { useEffect, useState } from 'react';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const NotificationComponent = () => {
    const [notifications, setNotifications] = useState([]);
    const [hasNewNotification, setHasNewNotification] = useState(false);

    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            stompClient.subscribe('/topic/notifications', (message) => {
                const notification = JSON.parse(message.body);
                setNotifications((prev) => [...prev, notification]);
                setHasNewNotification(true);
            });
        });

        return () => {
            if (stompClient) {
                stompClient.disconnect();
            }
        };
    }, []);

    return (
        <div>
            <div className="friend-page">
                {hasNewNotification && <span className="red-dot"></span>}
                {/* Rest of your friend page */}
            </div>
            <div>
                {notifications.map((notification, index) => (
                    <div key={index}>
                        {notification.sender} sent you a friend request: {notification.message}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default NotificationComponent;
