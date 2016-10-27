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


if(isset($_REQUEST["path"])){
	$path = $_REQUEST["path"];
	$scanned_directory = scandir($path);
	$directory_list = array();
	for($i = 0; $i < count($scanned_directory); $i++){
		if(is_dir($path . "/" . $scanned_directory[$i])){
			$temp = array();
			$temp["name"] = $scanned_directory[$i];
			$temp["type"] = "dir";
			$directory_list[] = $temp;
		}else{
			$temp = array();
			$temp["name"] = $scanned_directory[$i];
			$temp["type"] = "file";
			$directory_list[] = $temp;
		}
	}

	jsonReplyWithMessage(STATUS_SUCCESS, $directory_list);
}else{
	jsonReplyWithMessage(STATUS_FAILED, "Please Fill all the Paramters");
}
?>
