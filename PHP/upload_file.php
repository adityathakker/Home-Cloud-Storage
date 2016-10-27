<?php
header("Content-Type: application/json");
include("config.php");
define("STATUS_FAILED", "failed");
define("STATUS_SUCCESS","success");

function jsonReplyWithMessage($status, $message){
    $json = array();
    $json["status"] = $status;
    $json["message"] = $message;
    echo json_encode($json);
}

function jsonReply($status){
    $json = array();
    $json["status"] = $status;
    echo json_encode($json);
}

if(isset($_REQUEST["path"]) && isset($_REQUEST["username"]) && isset($_REQUEST["size"])){
    $path = $_REQUEST["path"];
    $username = $_REQUEST["username"];
    $size = $_REQUEST["size"];
    
    $sql_insert = "insert into upload_tasks_queue (path, username, size) values('".$path."', '".$username."', '".$size."')";
    $result = $conn->query($sql_insert);
    if($result){
        jsonReply(STATUS_SUCCESS);
    }else{
        jsonReplyWithMessage(STATUS_FAILED, "Error While Adding To Queue");
    }
}else{
    jsonReplyWithMessage(STATUS_FAILED, "Please Fill all the Paramters");
}
?>
