import { useState } from 'react';

export function ProfilePage(){

    const [formData, setFormData] = useState({
        dateOfBirth: '',
        bio:'', 
    });

    const [profilePicture, setSelectedFile] = useState(null);
    const [previewImageUrl, setPreviewImageUrl] = useState(null);

    const handleChange = (event) => {
        setFormData({...formData, [event.target.name]: event.target.value }); 
    };

    const handleChangeForImage = (event) => {
        const file = event.target.files[0]; // Access the selected file
        if (file) {
            setSelectedFile(file);
    
            const reader = new FileReader();
            reader.onload = (e) => {
              setPreviewImageUrl(e.target.result); // Set the data URL for preview
            };
            reader.readAsDataURL(file); // Read the file as a data URL
          }
    };


  
    return (  
        <div>
            <h1>Tell Us About Yourself</h1>
        <form>
        <label>
            Date of Birth:
            <input type="date" name='dateOfBirth'  onChange={handleChange} />
        </label><br />
        <label>
            Bio:
            <input type="textarea" name='bio' onChange={handleChange} />
        </label><br />
        <label>
            profilePhoto:
            <input type="file" name='profilePicture' onChange={handleChangeForImage} />
        </label><br />
        <button type="submit">Submit</button>
        </form>
        <p>
            <h3>Entered details:(for testing purpose)</h3>  {/* For testing only */}

            Date of Birth: {formData.dateOfBirth}<br />
            Bio: {formData.bio}<br />
            Profile Photo: <br />
            {previewImageUrl && ( // Conditionally render preview image
            <img src={previewImageUrl} alt="Selected profile picture preview" height={200} width={300}/>
            )}
      </p>
        </div>
        
    );
}
 