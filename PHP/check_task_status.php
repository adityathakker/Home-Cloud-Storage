<?php
header("Content-Type: application/json");
include("config.php");
define("STATUS_FAILED", "failed");
define("STATUS_SUCCESS","success");

function jsonReplyWithStatus($status, $executed, $status, $comment){
	$json = array();
	$json["status"] = $status;
	$json["executed"] = $executed;
	$json["result"] = $status;
	$json["comment"] = $comment;
	echo json_encode($json);
}

function jsonReplyWithMessage($status, $message){
	$json = array();
	$json["status"] = $status;
	$json["message"] = $message;
	echo json_encode($json);
}

if(isset($_REQUEST["type"]) && isset($_REQUEST["name"]) && isset($_REQUEST["path"]) && isset($_REQUEST["username"])){
	$type = $_REQUEST["type"];
	$name = $_REQUEST["name"];
	$path = $_REQUEST["path"];
	$username = $_REQUEST["username"];
	
	$sql_insert = "select * from create_tasks_queue where file_name='".$name."' and path='".$path."' and type='".$type."' and user='".$username."' order by timestamp desc limit 1";
	// echo $sql_insert;
	$result = $conn->query($sql_insert);
	if($result->num_rows > 0){
		$row = $result->fetch_assoc();
		jsonReplyWithStatus(STATUS_SUCCESS, $row["executed"], $row["result"], $row["comment"]);
	}else{
		jsonReplyWithMessage(STATUS_FAILED, "Nothing To Check");
	}
}else{
	jsonReplyWithMessage(STATUS_FAILED, "Please Fill all the Paramters");
}
?>
