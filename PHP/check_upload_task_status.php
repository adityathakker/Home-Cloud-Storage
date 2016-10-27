<?php
header("Content-Type: application/json");
include("config.php");
define("STATUS_FAILED", "failed");
define("STATUS_SUCCESS","success");

function jsonReplyWithStatus($status, $executed, $status, $comment){
	$json = array();
	$json["status"] = "success";
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

if(isset($_REQUEST["path"]) && isset($_REQUEST["username"]) && isset($_REQUEST["size"])){
	$path = $_REQUEST["path"];
	$username = $_REQUEST["username"];
	$size = $_REQUEST["size"];
	
	$sql_insert = "select * from upload_tasks_queue where path='".$path."' and size='".$size."' and username='".$username."' order by timestamp asc limit 1";
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
