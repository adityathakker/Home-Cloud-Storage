<?php
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

function redirect($url) {
    ob_start();
    header('Location: '.$url);
    ob_end_flush();
    die();
}

$select_from_upload_tasks_queue = "select * from upload_tasks_queue where executed='exe' order by timestamp asc, size asc limit 1";
$result_of_upload_tasks_queue = $conn->query($select_from_upload_tasks_queue);
if($result_of_upload_tasks_queue->num_rows <= 0){
	$select_from_create_tasks_queue = "select * from create_tasks_queue where executed='no' order by timestamp asc limit 1";
	$result_of_create_tasks_queue = $conn->query($select_from_create_tasks_queue);
	if($result_of_create_tasks_queue->num_rows > 0){
		$row_from_create_tasks_queue = $result_of_create_tasks_queue->fetch_assoc();

		$type = $row_from_create_tasks_queue["type"];
		$name = $row_from_create_tasks_queue["file_name"];
		$path = $row_from_create_tasks_queue["path"];
		$id = $row_from_create_tasks_queue["_id"];
		
		if($type == "file"){
			$tempFile = fopen($path ."/". $name, "w");
			fclose($tempFile);
			$update_task = "update create_tasks_queue set executed='yes', result='success' where _id=".$id;
			$result_of_update_task = $conn->query($update_task);
		}else{
			if (!mkdir($path . "/" . $name, 0777, true)) {
				$update_task = "update create_tasks_queue set executed='yes', result='failed', comment='Cannot Create Folder' where _id=".$id;
				$result_of_update_task = $conn->query($update_task);
			}else{
				$update_task = "update create_tasks_queue set executed='yes', result='success' where _id=".$id;
			$result_of_update_task = $conn->query($update_task);
			}
		}
		redirect("execute_from_queue.php");
	}



	$select_from_delete_tasks_queue = "select * from delete_tasks_queue where executed='no' order by timestamp asc limit 1";
	$result_of_delete_tasks_queue = $conn->query($select_from_delete_tasks_queue);
	if($result_of_delete_tasks_queue->num_rows > 0){
		$row_from_delete_tasks_queue = $result_of_delete_tasks_queue->fetch_assoc();

		$type = $row_from_delete_tasks_queue["type"];
		$name = $row_from_delete_tasks_queue["file_name"];
		$path = $row_from_delete_tasks_queue["path"];
		$id = $row_from_delete_tasks_queue["_id"];

		if($type == "file"){
			if (!unlink($path ."/". $name)){
				$update_task = "update delete_tasks_queue set executed='yes', result='failed', comment='Cannot Delete File' where _id=".$id;
				$result_of_update_task = $conn->query($update_task);
			}else{
				$update_task = "update delete_tasks_queue set executed='yes', result='success' where _id=".$id;
				$result_of_update_task = $conn->query($update_task);

			}
		}else{
			if (!rmdir($path ."/". $name)) {
				$update_task = "update delete_tasks_queue set executed='yes', result='failed', comment='Cannot Delete Folder' where _id=".$id;
				$result_of_update_task = $conn->query($update_task);
			}else{
				$update_task = "update delete_tasks_queue set executed='yes', result='success' where _id=".$id;
				$result_of_update_task = $conn->query($update_task);

			}
		}

	redirect("execute_from_queue.php");	
	}



	$select_from_upload_tasks_queue = "select * from upload_tasks_queue where executed='no' order by timestamp asc limit 1";
	$result_of_upload_tasks_queue = $conn->query($select_from_upload_tasks_queue);
	if($result_of_upload_tasks_queue->num_rows > 0){
		$row_from_upload_tasks_queue = $result_of_upload_tasks_queue->fetch_assoc();
		$update_task = "update upload_tasks_queue set executed='executing' where _id=".$row_from_upload_tasks_queue["_id"];
		$result_of_update_task = $conn->query($update_task);
	}
}


echo "<html><body>";
echo "<script>setTimeout(function(){window.location.href='execute_from_queue.php'},8000); </script>";
echo "</body></html>";
?>
