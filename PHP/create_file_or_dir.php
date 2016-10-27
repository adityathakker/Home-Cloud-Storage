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

if(isset($_REQUEST["type"]) && isset($_REQUEST["name"]) && isset($_REQUEST["path"]) && isset($_REQUEST["username"])){
	$type = $_REQUEST["type"];
	$name = $_REQUEST["name"];
	$path = $_REQUEST["path"];
	$username = $_REQUEST["username"];
	
	$sql_insert = "insert into create_tasks_queue (file_name, path, type, user) values('".$name."','".$path."','".$type."', '".$username."')";
	$result = $conn->query($sql_insert);
	if($result){
		jsonReply(STATUS_SUCCESS);
	}
}else{
	jsonReplyWithMessage(STATUS_FAILED, "Please Fill all the Paramters");
}
?>
