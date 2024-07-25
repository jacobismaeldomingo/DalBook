// src/components/Groups.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Groups = () => {
  // State variables for group creation form inputs
  const [groupName, setName] = useState('');
  const [faculty, setFaculty] = useState('');
  const [description, setDescription] = useState('');

  const [loading, setLoading] = useState(true);

  // State variable to store the list of groups
  const [groups, setGroups] = useState([] );

  // State variable to store the currently selected group
  const [selectedGroup, setSelectedGroup] = useState('');
  const [deleteUser, setDeleteUser] = useState('');
  const [userd, setUserd] = useState('');

  const [deletedGroup, setDeleteGroup] =useState();

  const [userGroup, setUserGroup] = useState();




  

  // State variable for the member input field
  const [member, setMember] = useState('');

  // Effect to fetch all groups on component mount
  useEffect(() => {
    axios.get( `http://localhost:8085/Group/Groups`).then(response => {
        setGroups(response.data);
        setLoading(false);
    })
    .catch (error => {
        console.error("There was an error fetching the groups")
    })
  }
  , []);
  const getUserGroups = () =>{
    axios.get('http://localhost:8085/api/user/Groups/${userGroup}').then(reponse =>
    {setUserGroup()}
    )
  }

  // Function to handle the creation of a new group
  const handleCreateGroup = async (event) => {
    event.preventDefault();
    const newGroup = { groupName, faculty, description };  // Create a new group object
    //const response = await axios.post('http://localhost:8085/Group/createGroup', newGroup).then();
    axios.post('http://localhost:8085/Group/createGroup', newGroup).then( response =>  {
    // Send POST request to create group
   //setSelectedGroup([selectedGroup, response.data]);  
    setName('');  // Clear the form inputs
    setFaculty('');
    setDescription('');
}).catch(error => {
    console.error('There was an error creating the group!', error);
  });


  };

  // Function to handle adding a member to the selected group
  const handleAddMember = async (event) => {
    event.preventDefault();
    //const response = await axios.put(`http://localhost:8085/Group/addUser?GroupId=${selectedGroup.id}&userId=${member}`);  // Send PUT request to add member
    axios.put(`http://localhost:8085/Group/addUser?GroupId=${selectedGroup}&userId=${member}`).then(response => {  // Send PUT request to add member

    setMember('');
    setSelectedGroup('');
}).catch(error => {
    console.error('There was an error adding members!', error);
  });  // Clear the member input field
  };

  const deleteGroup =  (deletedGroup ) =>{
    axios.delete(`http://localhost:8085/Group/deleteGroup/${deletedGroup}`)


  }

  // Function to handle removing a member from the selected group
  const handleRemoveMember = async (event) => {
    event.preventDefault();
    axios.post(`http://localhost:8085/Group/deleteUser?GroupId=${deleteUser}&userId=${userd}`).then(reponse =>{
      // Send POST request to remove member
        setDeleteUser('');
        setUserd('');
    }).catch(error => {
        console.error('There was an error removing members!', error);
      });  };

  return (
      <div style={styles.container}>
        <h2 style={styles.header}>Group Creation</h2>
        {/* Group creation form */}
        <form onSubmit={handleCreateGroup} style={styles.form}>
          <input
            type="text"
            value={groupName}
            onChange={(e) => setName(e.target.value)}
            placeholder="Group Name"
            required
            style={styles.input}
          />
          <input
            type="text"
            value={faculty}
            onChange={(e) => setFaculty(e.target.value)}
            placeholder="Faculty"
            required
            style={styles.input}
          />
          <input
            type="text"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Description"
            required
            style={styles.input}
          />
          <button type="submit" style={styles.button}>Create Group</button>
        </form>
  
        <div style={styles.subSection}>
          <h3 style={styles.subHeader}>Add User</h3>
          <form onSubmit={handleAddMember} style={styles.form}>
            <input
              type="text"
              value={member}
              onChange={(e) => setMember(e.target.value)}
              placeholder="Add Member ID"
              required
              style={styles.input}
            />
            <input
              type="text"
              value={selectedGroup}
              onChange={(e) => setSelectedGroup(e.target.value)}
              placeholder="Add Group ID"
              required
              style={styles.input}
            />
            <button type="submit" style={styles.button}>Add Member</button>
          </form>
        </div>
  
        <div style={styles.subSection}>
          <h4 style={styles.subHeader}>Remove User</h4>
          <form onSubmit={handleRemoveMember} style={styles.form}>
            <input
              type="text"
              value={userd}
              onChange={(e) => setUserd(e.target.value)}
              placeholder="User ID"
              required
              style={styles.input}
            />
            <input
              type="text"
              value={deleteUser}
              onChange={(e) => setDeleteUser(e.target.value)}
              placeholder="Group ID"
              required
              style={styles.input}
            />
            <button type="submit" style={styles.button}>Remove Member</button>
          </form>
        </div>
  
        <div style={styles.groupsSection}>
          <h1 style={styles.groupsHeader}>Groups</h1>
          <ul style={styles.groupList}>
            {groups.map((group) => (
              <li key={group.id} style={styles.groupListItem}>
                {group.groupName}
                <button onClick={() => deleteGroup(group.id)} style={styles.removeButton}>Delete</button>
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
    form: {
      display: 'flex',
      flexDirection: 'column',
    },
    input: {
      marginBottom: '10px',
      padding: '10px',
      borderRadius: '4px',
      border: '1px solid #ccc',
      fontSize: '16px',
    },
    button: {
      padding: '10px 15px',
      borderRadius: '4px',
      border: 'none',
      backgroundColor: '#007BFF',
      color: '#fff',
      fontSize: '16px',
      cursor: 'pointer',
      transition: 'background-color 0.3s',
    },
    buttonHover: {
      backgroundColor: '#0056b3',
    },
    subSection: {
      marginTop: '20px',
    },
    subHeader: {
      marginBottom: '10px',
      color: '#555',
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

export default Groups;

