import React, {useState, useEffect, useCallback} from "react";
import logo from './logo.svg';
import './App.css';
import axios from "axios";
import {useDropzone} from 'react-dropzone'

const ImageEntries = () =>{

const [imageEntries, setImageEntries] = useState([]);

  const fetchImageEntries = () =>{
    axios.get("http://localhost:8080/api/v1/image-entry").then(res =>{
      console.log(res);
      const data = res.data;
      setImageEntries(res.data);
    });
  };

  useEffect(() =>{
  fetchImageEntries();
  }, []);

  return imageEntries.map((imageEntry, index) =>{

    return(

      <div key={index}>
        {imageEntry.imageId ? <img src={`http://localhost:8080/api/v1/image-entry/${imageEntry.imageId}/image/download`}/> : null}
        <br/>
        <br/>
        <Dropzone imageId = {imageEntry.imageId}/>
        <br/>
      </div>
    )
  })
};
function Dropzone({imageId}) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    
    console.log(file);
    
    const formData = new FormData();
    formData.append("file", file);

    axios.post(`http://localhost:8080/api/v1/image-entry/${imageId}/image/upload`, formData,
    {
      headers:{
        "Content-Type": "multipart/form-data"
      }
    }
  ).then(() =>{
    console.log("File uploaded successfully")
  }).catch(err => {
    console.log(err);
  })
;  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop profile image, or click to select files</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
    <h1>Welcome to Face Detector</h1>
      <ImageEntries />
      
    </div>
  );
}

export default App;
