import React from "react";
import "../css/Feed.css";
import {
  IconUsers,
  IconCalendarStar,
  IconFlag,
  IconHearts,
  IconChevronDown,
  IconUser,
  IconSearch,
  IconDots,
  IconPhoto,
  IconMoodSmile,
  IconThumbUp,
  IconMessageCircle,
  IconShare3,
} from "@tabler/icons-react";

function Feed() {
  return (
    <div className="main">
      <div className="left-side">
        <div className="profile">
          <img
            src="/images/avatar-1.jpeg"
            alt="profile-picture"
            style={{ padding: "1rem" }}
          />
          John Doe
        </div>
        <div className="panel">
          <IconUsers stroke={2} />
          <div>Friends</div>
        </div>
        <div className="panel">
          <IconCalendarStar stroke={2} />
          <div>Events</div>
        </div>
        <div className="panel">
          <IconFlag stroke={2} />
          <div>Pages</div>
        </div>
        <div className="panel">
          <IconHearts stroke={2} />
          <div>Fundraisers</div>
        </div>
        <div className="panel">
          <IconChevronDown stroke={2} />
          <div>See More</div>
        </div>
        <div className="border"></div>
        <br />
        <div className="pages">
          <img
            src="/images/dalhousie-logo.png"
            alt="logo"
            style={{ padding: "1rem" }}
          />
          Dalhousie University
        </div>
        <div className="pages">
          <img
            src="/images/dalhousie-tigers-logo.png"
            alt="logo"
            style={{ padding: "1rem" }}
          />
          Dalhousie University Football
        </div>
        <div className="pages">
          <img src="/images/pulse.png" alt="logo" style={{ padding: "1rem" }} />
          Brigthspace Pulse
        </div>
        <div className="dropdown-panels">
          <IconChevronDown stroke={2} className="chevron" />
          <div>See More</div>
        </div>
      </div>
      <div className="timeline">
        <div className="addStory">
          <div className="story">
            <img
              src="/images/avatar-2.jpeg"
              alt="logo"
              style={{ height: "50px", borderRadius: "50%" }}
            />
            <br />
            John Doe
          </div>
          <div className="story">
            <img
              src="/images/avatar-3.jpeg"
              alt="logo"
              style={{ height: "50px", borderRadius: "50%" }}
            />
            <br />
            John Doe
          </div>
          <div className="story">
            <img
              src="/images/avatar-3.jpeg"
              alt="logo"
              style={{ height: "50px", borderRadius: "50%" }}
            />
            <br />
            John Doe
          </div>
          <div className="story">
            <img
              src="/images/avatar-4.jpeg"
              alt="logo"
              style={{ height: "50px", borderRadius: "50%" }}
            />
            <br />
            John Doe
          </div>
        </div>
        <div className="create-post">
          <div className="text">
            <div className="user-post">
              <img
                src="/images/avatar-1.jpeg"
                alt="profile-picture"
                style={{ height: "50px", padding: "1rem" }}
              />
              <input type="Mind" placeholder="What's on your mind, John?" />
            </div>
            <div className="border-post"></div>
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
          <div className="posted">
            <div className="post">
              <div className="profile-picture">
                <img
                  src="/images/avatar-1.jpeg"
                  alt="profile-picture"
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
            <div className="media">
              <img
                src="/images/post.jpg"
                alt="posted-image"
                style={{ height: "auto", width: "100%" }}
              />
            </div>
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
          <div className="posted">
            <div className="post">
              <div className="profile-picture">
                <img
                  src="/images/avatar-1.jpeg"
                  alt="profile-picture"
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
            <div className="media">
              <img
                src="/images/post.jpg"
                alt="posted-image"
                style={{ height: "auto", width: "100%" }}
              />
            </div>
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
          <div className="contacts">Contacts</div>
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
          <div className="profiles">
            <img
              src="/images/avatar-2.jpeg"
              alt="profile-picture"
              style={{ padding: "1rem" }}
            />
            John Doe
          </div>
          <div className="profiles">
            <img
              src="/images/avatar-3.jpeg"
              alt="profile-picture"
              style={{ padding: "1rem" }}
            />
            John Doe
          </div>
          <div className="profiles">
            <img
              src="/images/avatar-4.jpeg"
              alt="profile-picture"
              style={{ padding: "1rem" }}
            />
            John Doe
          </div>
          <div className="profiles">
            <img
              src="/images/avatar-5.jpeg"
              alt="profile-picture"
              style={{ padding: "1rem" }}
            />
            John Doe
          </div>
        </div>
      </div>
    </div>
  );
}

export default Feed;
