import React, { useEffect, useState } from "react";
import friendService from "../../services/FriendService";
import "./FriendRequest.css";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
    IconSearch,
  } from "@tabler/icons-react";

const SearchPeople = () => {
    const [searchResult, setSearchResult] = useState([]);
    const [searchName, setSearchName] = useState();
    const [userId, setUserId] = useState(null);
    const navigate = useNavigate();

    const handleInputChange = (event) => {
      setSearchName(event.target.value);
    //   console.log(searchName);
    };
   

    const handleSearch = () => {
        // Only perform search if searchName is not empty
        if (searchName) {
          friendService
            .searchPeople(searchName)
            .then((response) => {
              setSearchResult(response.data);
            })
            .catch((error) => {
              console.error("Error fetching friends:", error);
              toast.warn("An error occurred. Please try again!");
            });
        }
      };
    
      useEffect(() => {
        const storedUserId = localStorage.getItem("userId");
        if (storedUserId) {
          setUserId(storedUserId);
        }
      }, []);

      return (
        
        <div>


        <div className="search-bar">
            <IconSearch stroke={2} />
            <input placeholder="Search Dalbook" type="Search"
                    value={searchName}
                    onChange={handleInputChange}
            />
            <button onClick={handleSearch}>Search</button>
          </div>

          <ToastContainer />
      <h2 style={{ padding: "1.5rem", paddingBottom: "0" }}>Your Result</h2>
      <div className="friends-list">
        {searchResult.length > 0 ? (
          searchResult.map((result) => (
            <div
              key={result.id}
              className="friends-name"
              style={{ cursor: "pointer" }}
            >
              <img
                src={
                  result.profilePic
                    ? `http://localhost:8085${result.profilePic}`
                    : "/images/dalhousie-logo.png"
                }
                alt=""
                style={{ padding: "1rem" }}
              />
              <div>{result.name}</div>
            </div>
          ))
        ) : (
          <p>No results found</p>
        )}
      </div>
    </div>
  );
};


export default SearchPeople;
