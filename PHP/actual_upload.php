<?php
include("config.php");
$select_from_upload_tasks_queue = "select * from upload_tasks_queue where executed='exe' order by timestamp desc limit 1";
$result_of_upload_tasks_queue = $conn->query($select_from_upload_tasks_queue);
if($result_of_upload_tasks_queue->num_rows > 0){
	$row_from_upload_tasks_queue = $result_of_upload_tasks_queue->fetch_assoc();

	$path = $row_from_upload_tasks_queue["path"];
	$id = $row_from_upload_tasks_queue["_id"];


	$target_file = $path ."/". basename($_FILES["myFile"]["name"]);
	$uploadOk = 1;
	$imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);

	if (file_exists($target_file)) {
	    $update_task = "update upload_tasks_queue set executed='yes', result='failed', comment='File Already Exists' where _id=".$id;
		$result_of_update_task = $conn->query($update_task);
	    $uploadOk = 0;
	}
	// Check file size
	if ($_FILES["myFile"]["size"] > 50000000) {
	    $update_task = "update upload_tasks_queue set executed='yes', result='failed', comment='File is Too Big' where _id=".$id;
			$result_of_update_task = $conn->query($update_task);
	    $uploadOk = 0;
	}

	// Check if $uploadOk is set to 0 by an error
	if ($uploadOk != 0) {
	    if (move_uploaded_file($_FILES["myFile"]["tmp_name"], $target_file)) {
	        $update_task = "update upload_tasks_queue set executed='yes', result='success', comment='Uploaded' where _id=".$id;
			$result_of_update_task = $conn->query($update_task);
	    } else {
	        $update_task = "update upload_tasks_queue set executed='yes', result='failed', comment='Error Uploading' where _id=".$id;
			$result_of_update_task = $conn->query($update_task);
	    }
	} 



sleep(15);
redirect("execute_from_queue.php");	
}
?>