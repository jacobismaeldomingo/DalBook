// src/components/Groups.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const GroupList = () => {

  const [groups, setGroups] = useState([] );
  

 



const userId =localStorage.getItem;
  

  // Effect to fetch all groups on component mount
  useEffect(() => {
    axios.get( `http://localhost:8085/api/user/Groups/${userId}`).then(response => {
        setGroups(response.data);
       // setLoading(false);
    })
    .catch (error => {
        console.error("There was an error fetching the groups")
    })
  }
  , []);
  

  const deleteGroup =  (deletedGroup) =>{
    axios.post(`http://localhost:8085/Group/deleteUser?GroupId=${deletedGroup}&userId=${userId}`)

    
}

// Function to handle removing a member from the selected group

  

  return (
      <div style={styles.container}>
        
        <div style={styles.groupsSection}>
          <h1 style={styles.groupsHeader}>Groups</h1>
          <ul style={styles.groupList}>
            {groups.map((group) => (
              <li key={group.id} style={styles.groupListItem}>
                {group.groupName}
                <button onClick={() => deleteGroup(group.id)} style={styles.removeButton}>Leave</button>
              </li>
            ))}
          </ul>
        </div>
      </div>
    );
  };
  
  const styles = {
    container: {
      fontFamily: 'Arial, sans-serif',
      padding: '20px',
      maxWidth: '600px',
      margin: '0 auto',
      backgroundColor: '#f9f9f9',
      borderRadius: '8px',
      boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
    },
    header: {
      textAlign: 'center',
      color: '#333',
      marginBottom: '20px',
    },
    
    groupsSection: {
      marginTop: '30px',
    },
    groupsHeader: {
      textAlign: 'center',
      color: '#333',
      marginBottom: '20px',
    },
    groupList: {
      listStyleType: 'none',
      padding: '0',
    },
    groupListItem: {
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center',
      padding: '10px',
      borderBottom: '1px solid #ccc',
    },
    removeButton: {
      padding: '5px 10px',
      borderRadius: '4px',
      border: 'none',
      backgroundColor: '#FF4136',
      color: '#fff',
      cursor: 'pointer',
      transition: 'background-color 0.3s',
    },
    removeButtonHover: {
      backgroundColor: '#c12c26',
    },
  }

export default GroupList;


