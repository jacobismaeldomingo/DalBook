// Home Page
import React from "react";
import "./Header.css";
import Feed from "../feed/Feed";
import Header from "./Header";

function Home() {
  return (
    <div className="homepage">
      <Header />
      <Feed />
    </div>
  );
}

export default Home;
