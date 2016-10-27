<?php
header('Content-Type: application/json');
include("config.php");
define("STATUS_FAILED", "failed");
define("STATUS_SUCCESS","success");

function jsonReply($status){
	$json = array();
	$json["status"] = $status;
	echo json_encode($json);
}

function jsonReplyWithMessage($status, $message){
	$json = array();
	$json["status"] = $status;
	$json["message"] = $message;
	echo json_encode($json);
}

if(isset($_REQUEST["username"]) && isset($_REQUEST["password"]) && isset($_REQUEST["name"])){
	$username = $_REQUEST["username"];
	$password = $_REQUEST["password"];
	$name = $_REQUEST["name"];

	$sql = "insert into users(username, password, name) values('".$username."','".$password."','".$name."')";
	$result = $conn->query($sql);
	if($result){
		if(mkdir("users_dir/".$username, 0777, true)){
			jsonReply(STATUS_SUCCESS);
		}else{
			jsonReplyWithMessage(STATUS_FAILED, "Cannot Create New Account. Something Went Wrong");
			$sql_remove = "delete from users where username='".$username."'";
			$result_remove = $conn->query($sql_remove);
		}
	}else{
		jsonReplyWithMessage(STATUS_FAILED, "Cannot Create New Account. Something Went Wrong");
	}
}else{
	jsonReplyWithMessage(STATUS_FAILED, "Please Fill All The Fields");
}
?>