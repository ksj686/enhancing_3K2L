/**
 * 
 */
function updateFileName(){
	const fileInput = document.getElementById("file");
	const fileNameInput = document.getElementById("fileName");
	
	if(fileInput.files.length > 0){
		let fileNames = Array.from(fileInput.files).map(file => file.name).join(", ");
		fileNameInput.value = fileNames;
	}	
}