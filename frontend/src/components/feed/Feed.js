import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Feed.css";
import {
  IconUsers,
  IconCalendarStar,
  IconFlag,
  IconChevronDown,
  IconUser,
  IconSearch,
  IconDots,
  IconPhoto,
  IconMoodSmile,
  IconThumbUp,
  IconMessageCircle,
  IconShare3,
  IconCircleCheckFilled,
  IconClockFilled,
  IconSquareMinusFilled,
  IconCircleXFilled,
  IconUsersGroup,
} from "@tabler/icons-react";
import friendService from "../../services/FriendService";
import Post from "./Post";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function Feed() {
  const [friends, setFriends] = useState([]);
  const navigate = useNavigate();
  const [userId, setUserId] = useState(null);
  const [user, setUser] = useState(null);
  const [showPostPopup, setShowPostPopup] = useState(false); // State to show/hide the post popup
  const [posts, setPosts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [users, setUsers] = useState({});

  const userProfile = () => {
    navigate("/profile");
  };

  const friendsPage = () => {
    navigate("/friends");
  };

  const groupsPage = () => {
    navigate("/groupDashboard");
  };

  const categoryOfDayPage = () => {
    navigate("/categoryOftheDay");
  };

  const pages = () => {
    navigate("/pages");
  };

  useEffect(() => {
    const storedUserId = localStorage.getItem("userId");
    const storedUserEmail = localStorage.getItem("userEmail");
    if (storedUserId && storedUserEmail) {
      setUserId(storedUserId);

      // Get Current User Information
      const fetchUser = async () => {
        try {
          const response = await axios.get(
            `http://localhost:8085/api/user/getByEmail/${storedUserEmail}`
          );
          setUser(response.data);
          console.log(response.data);
          console.log("User information retrieved successfully");
        } catch (error) {
          console.log("Error fetching user", error);
        }
      };
      fetchUser();

      // Get Current User Friends
      friendService
        .getFriends(storedUserId)
        .then((response) => {
          setFriends(response.data);
          console.log("Success fetching friends of user id:", userId);
        })
        .catch((error) => {
          console.error("Error fetching friends:", error);
          toast.warn("An error occurred. Please try again!");
        });
    }

    const retrieveUsers = async (postsData) => {
      // Fetch user details for each post
      const userPromises = postsData.map((post) =>
        axios.get(`http://localhost:8085/api/user/getById/${post.userId}`)
      );
      const userResponses = await Promise.all(userPromises);

      // Create a mapping of user IDs to user data
      const userMap = userResponses.reduce((acc, response) => {
        const user = response.data;
        acc[user.id] = user;
        return acc;
      }, {});
      setUsers(userMap);
    };

    const fetchPosts = async () => {
      try {
        const response = await axios.get("http://localhost:8085/api/posts");
        const posts = response.data;

        // Sort posts from latest to oldest based on a `timestamp` field
        posts.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));

        retrieveUsers(posts); // Assuming retrieveUsers is used to process posts
        console.log(posts);
        setPosts(posts);
        setIsLoading(false);
      } catch (error) {
        console.error("Error fetching posts:", error);
        setIsLoading(false);
      }
    };

    fetchPosts();
  }, [userId]);

  const getStatusIcon = (status) => {
    switch (status) {
      case "Available":
        return (
          <IconCircleCheckFilled color="green" style={{ paddingLeft: "5px" }} />
        );
      case "Away":
        return (
          <IconClockFilled color="orange" style={{ paddingLeft: "5px" }} />
        );
      case "Busy":
        return (
          <IconSquareMinusFilled color="red" style={{ paddingLeft: "5px" }} />
        );
      case "Offline":
        return (
          <IconCircleXFilled color="gray" style={{ paddingLeft: "5px" }} />
        );
      default:
        return null;
    }
  };

  const viewFriendProfile = (friendEmail) => {
    navigate(`/friendProfile/${friendEmail}`);
  };

  return (
    <div className="main">
      <div className="left-side">
        <div
          className="profile"
          onClick={userProfile}
          style={{ cursor: "pointer" }}
        >
          <img
            src={
              user && user.profilePic
                ? `http://localhost:8085${user.profilePic}`
                : "/images/dalhousie-logo.png"
            }
            alt=""
            style={{ padding: "1rem" }}
          />
          {user ? (
            <div className="user-name">
              {user.firstName + " " + user.lastName}
              {getStatusIcon(user.status)}
            </div>
          ) : (
            "Loading..."
          )}
        </div>
        <div className="panel" onClick={friendsPage}>
          <IconUsers stroke={2} />
          <div>Friends</div>
        </div>
        <div className="panel" onClick={groupsPage}>
          <IconUsersGroup stroke={2} />
          <div>Groups</div>
        </div>
        <div className="panel" onClick={categoryOfDayPage}>
          <IconCalendarStar stroke={2} />
          <div>Category of the Day</div>
        </div>
        <div className="panel" onClick={pages}>
          <IconFlag stroke={2} />
          <div>Pages</div>
        </div>
        <div className="panel">
          <IconChevronDown stroke={2} />
          <div>See More</div>
        </div>
        <div className="border"></div>
        <br />
        <a
          href="https://www.dal.ca/"
          target="_blank"
          rel="noopener noreferrer"
          style={{ textDecoration: "none", color: "black" }}
        >
          <div className="pages">
            <img
              src="/images/dalhousie-logo.png"
              alt="logo"
              style={{ padding: "1rem" }}
            />
            Dalhousie University
          </div>
        </a>
        <a
          href="https://daltigers.ca/sports/fball/index"
          target="_blank"
          rel="noopener noreferrer"
          style={{ textDecoration: "none", color: "black" }}
        >
          <div className="pages">
            <img
              src="/images/dalhousie-tigers-logo.png"
              alt="logo"
              style={{ padding: "1rem" }}
            />
            Dalhousie University Football
          </div>
        </a>
        <a
          href="https://dal.brightspace.com/d2l/home"
          target="_blank"
          rel="noopener noreferrer"
          style={{ textDecoration: "none", color: "black" }}
        >
          <div className="pages">
            <img
              src="/images/pulse.png"
              alt="logo"
              style={{ padding: "1rem" }}
            />
            Brightspace Pulse
          </div>
        </a>
        <div className="dropdown-panels">
          <IconChevronDown stroke={2} className="chevron" />
          <div>See More</div>
        </div>
      </div>
      <div className="timeline">
        <div className="create-post">
          <div className="text">
            <div className="user-post">
              <img
                src={
                  user && user.profilePic
                    ? `http://localhost:8085${user.profilePic}`
                    : "/images/dalhousie-logo.png"
                }
                alt=""
                style={{ height: "50px", padding: "1rem" }}
              />
              <input
                type="Mind"
                placeholder={`What's on your mind, ${
                  user ? user.firstName : "User"
                }?`}
                onClick={() => setShowPostPopup(true)}
              />
            </div>
            {/* <div className="border-post"></div> */}
            <div className="post-icons">
              <div className="icon">
                <div className="post-icon">
                  <IconPhoto stroke={2} color="green" />
                  <div>Photo/Video</div>
                </div>
                <div className="post-icon">
                  <IconMoodSmile stroke={2} color="orange" />
                  <div>Feeling/Activity</div>
                </div>
              </div>
            </div>
          </div>
          {showPostPopup && (
            <div className="post-popup">
              <div className="post-popup-content">
                <Post />
                <button
                  className="post-popup-close"
                  onClick={() => setShowPostPopup(false)}
                >
                  Close
                </button>
              </div>
            </div>
          )}
          <div>
            {isLoading
              ? "loading"
              : posts.map((post) => (
                  <div className="posted" key={post.postId}>
                    <div className="post">
                      <div className="feed-profile-picture">
                        <img
                          src={
                            users[post.userId]?.profilePic
                              ? `http://localhost:8085${
                                  users[post.userId]?.profilePic
                                }`
                              : "/images/dalhousie-logo.png"
                          }
                          alt=""
                          style={{ height: "50px", padding: "1rem" }}
                        />
                        <div className="users-name">
                          {users[post.userId]?.firstName || "Unknown"}{" "}
                          {users[post.userId]?.lastName || "User"}
                        </div>
                      </div>
                      <div className="edit">
                        <IconDots stroke={2} />
                      </div>
                    </div>
                    <div className="caption">{post.description}</div>
                    {post.mediaUrl && <img src={post.mediaUrl} alt="post" />}
                    <p>{post.feeling}</p>
                    <div className="reactions">
                      <div className="like">
                        <IconThumbUp stroke={2} />
                        Like
                      </div>
                      <div className="comment">
                        <IconMessageCircle stroke={2} />
                        Comment
                      </div>
                      <div className="share">
                        <IconShare3 stroke={2} />
                        Share
                      </div>
                    </div>
                  </div>
                ))}
          </div>
          <div className="posted">
            <div className="post">
              <div className="feed-profile-picture">
                <img
                  src="/images/avatar-1.jpeg"
                  alt=""
                  style={{ height: "50px" }}
                ></img>
                <div>John Doe</div>
                <div className="update">Updated his cover image.</div>
              </div>
              <div className="edit">
                <IconDots stroke={2} />
              </div>
            </div>
            <div className="caption">Feeling good today!</div>
            <br />
            {/* <div className="media">
              <img
                src="/images/post.jpg"
                alt=""
                style={{ height: "auto", width: "100%" }}
              />
            </div> */}
            <div className="reactions">
              <div className="like">
                <IconThumbUp stroke={2} />
                Like
              </div>
              <div className="comment">
                <IconMessageCircle stroke={2} />
                Comment
              </div>
              <div className="share">
                <IconShare3 stroke={2} />
                Share
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="right-side">
        <div className="contact">
          <div className="contacts">Friends</div>
          <div className="chat-icon">
            <div className="icons">
              <IconUser stroke={2} />
            </div>
            <div className="icons">
              <IconSearch stroke={2} />
            </div>
            <div className="icons">
              <IconDots stroke={2} />
            </div>
          </div>
        </div>
        <div className="concise">
          {friends.map((friend) => (
            <div
              key={friend.id}
              className="profiles name"
              onClick={() => viewFriendProfile(friend.email)}
              style={{ cursor: "pointer" }}
            >
              <img
                src={
                  friend.profilePic
                    ? `http://localhost:8085${friend.profilePic}`
                    : "/images/dalhousie-logo.png"
                }
                alt=""
                style={{ padding: "1rem" }}
              />
              {friend.firstName + " " + friend.lastName}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Feed;
