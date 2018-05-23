<?php
    require("password.php");
    $db = mysqli_connect("host", "user", "password", "dtb");
    
    $username = $_POST["username"];
    $password = $_POST["password"];
    
    //$username = 'daleru';
    //$password = 'dalerp';
    
    //$passwordHashed = password_hash($password, PASSWORD_DEFAULT);

    $sql = "SELECT * FROM AlifboUser WHERE username = '$username' LIMIT 1";
    $query = mysqli_query($db, $sql);
    $row = mysqli_fetch_array($query);
    
    $id_db = $row['user_id']; //for session id
    $age_db = $row['age']; //user info
    $name_db = $row['name']; //user info
    $username_db = $row['username']; //verification
    $password_db = $row['password']; //verification
    
    //$password_dbHashed = password_hash($password_db, PASSWORD_DEFAULT);
    
    $response = array();
    $response["success"] = false;  
    
    //echo $passwordHashed;
    //echo "@@@@@@@@@@@@@";
    //echo $password_db;
    
        if ($password==$password_db) {
            $response["success"] = true;  
            $response["name"] = $name_db;
            $response["age"] = $age_db;
        }
        
    echo json_encode($response);
    
?>