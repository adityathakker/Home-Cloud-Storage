<?php
header('Content-Type: application/json');
include("config.php");
define("STATUS_FAILED", "failed");
define("STATUS_SUCCESS","success");

function jsonReplyWithMessage($status, $message){
	$json = array();
	$json["status"] = $status;
	$json["message"] = $message;
	echo json_encode($json);
}

if(isset($_REQUEST["username"]) && isset($_REQUEST["password"])){
	$username = $_REQUEST["username"];
	$password = $_REQUEST["password"];

	$sql = "select * from users where username='".$username."' and password='".$password."'";
	$result = $conn->query($sql);
	if($result->num_rows > 0){
		jsonReplyWithMessage(STATUS_SUCCESS, $result->fetch_assoc()["username"]);
	}else{
		jsonReplyWithMessage(STATUS_FAILED, "Username and Password Combination Is Wrong");
	}
}else{
	jsonReplyWithMessage(STATUS_FAILED, "Email/Password is Empty");
}
?>