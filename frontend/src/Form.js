import { useState } from 'react';
    
        {/*xxxxxxxxxxxxx    RegistrationPage Function     xxxxxxxxxxxxx*/}

    function RegistrationPage({ onNext }){

        const [formData, setFormData] = useState({
            firstname:'', 
            lastname: '',
            email: '',
            password:'',
        });

        const handleChange = (event) => {
            setFormData({...formData, [event.target.name]: event.target.value, }); 
        };

        const handleSubmit = (event) => {
            event.preventDefault();
            onNext(); // Call the onNext function to show the ProfilePage
        };

    return (
        <div>
            <h1>Registration Page</h1>
        <form>
        <label>
            Enter your First Name:
            <input type="text" name='firstname'  onChange={handleChange} />
        </label><br />
        <label>
            Enter your Last Name:
            <input type="text" name='lastname' onChange={handleChange} />
        </label><br />
        <label>
            Enter your Dal Email:
            <input type="email" name='email' onChange={handleChange} />
        </label><br />
        <label>
            Set your password:s
            <input type="password" name='password' onChange={handleChange} />
        </label><br />
        <button type="submit" onClick={handleSubmit}>Submit</button>
        </form>
        <p>
            <h3>Entered details:(for testing purpose)</h3>  {/* For testing only */}
            fname: {formData.firstname}<br />
            lname: {formData.lastname}<br />
            email: {formData.email}<br />
            password: {formData.password}<br />
        </p>
        </div>
    );
}
        {/*xxxxxxxxxxxxx  ProfilePage Function   xxxxxxxxxxxxxxxxx*/}

function ProfilePage(){

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


function Form() {

    const [step, setStep] = useState(1); // State to manage which component to display

    // Function to go to the ProfilePage
    const goToProfilePage = () => {
        setStep(2); // Change the step to 2 to show the ProfilePage
    };

    return (
        <div>
            {step === 1 && <RegistrationPage onNext={goToProfilePage} />}
            {step === 2 && <ProfilePage />}
        </div>
    );
}

export default Form;
