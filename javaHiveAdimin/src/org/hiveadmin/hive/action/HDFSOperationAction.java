/**  
* @Project: javaHiveAdimin
* @Title: HDFSOperationAction.java
* @Package org.hiveadmin.hive.action
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 20, 2013 10:20:35 AM
* @version V1.0  
*/
package org.hiveadmin.hive.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.hadoop.fs.FileStatus;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext;
import org.hiveadmin.hdfs.utils.HDFSUtils;
import org.hiveadmin.hive.beans.User;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName HDFSOperationAction
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 10:20:35 AM
 */
@Component
public class HDFSOperationAction extends ActionSupport {
	
	HDFSUtils hdfsUtils;
	private String dirName;
	private String errorMsg;
	private boolean isroot;
	private String fileName;
	private String remotepath;
	private String remoteFileName;
	private String localpath;
	
	private String newName;
	private String oldName;
	private String listfilepath;
	
	private String title;
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	
	Logger log = Logger.getLogger(HDFSOperationAction.class);
	private FileStatus[] fileStatusArray;
	private String savePath;
		
	public String getListfilepath() {
		return listfilepath;
	}
	public void setListfilepath(String listfilepath) {
		this.listfilepath = listfilepath;
	}

	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	
	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}
	public String getRemotepath() {
		return remotepath;
	}
	public void setRemotepath(String remotepath) {
		this.remotepath = remotepath;
	}
	public String getLocalpath() {
		return localpath;
	}
	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public HDFSUtils getHdfsUtils() {
		return hdfsUtils;
	}
	@Resource(name="hdfsUtils")
	public void setHdfsUtils(HDFSUtils hdfsUtils) {
		this.hdfsUtils = hdfsUtils;
	}
	
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public boolean isIsroot() {
		return isroot;
	}
	public void setIsroot(boolean isroot) {
		this.isroot = isroot;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	
	public String getRemoteFileName() {
		return remoteFileName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public FileStatus[] getFileStatusArray() {
		return fileStatusArray;
	}
	public void setFileStatusArray(FileStatus[] fileStatusArray) {
		this.fileStatusArray = fileStatusArray;
	}
	public String setSavePath() throws Exception{
		return ServletActionContext.getServletContext().getRealPath("WEB-INF/"+savePath);
	}
	
	
	public void judgeRoot(){
		User curUser = (User)ServletActionContext.getContext().getSession().get("userInfo");
		if(curUser.getUsergroup().equals("admin")){
			log.info("current user is admin.");
			isroot = true;
		}else {
			log.info("current user is not admin");
			isroot=false;
		}
	}
	
	public String mkdirs(){
		try {
			judgeRoot();
			
			hdfsUtils.mkdirs(hdfsUtils.getFileSystem(), dirName, isroot);
			log.info("makedirs success. [dirName:"+dirName+"]");
		} catch (Exception e) {
			log.error("mkdirs failed. [dirName:"+dirName+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "mkdirs failed. [dirName:"+dirName+"][excpetion msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("mkdirs success. [dirName:"+dirName+"]");
		return SUCCESS;
	}
	public String deletedirs(){
		judgeRoot();
		try {
			hdfsUtils.rmdirs(hdfsUtils.getFileSystem(), dirName, isroot);
		} catch (Exception e) {
			log.error("rm dirs failed. [dirName:"+dirName+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "rm dirs failed. [dirName:"+dirName+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("rm dirs success. [dirName:"+dirName+"]");
		return SUCCESS;
	}
	
	public String mkfile(){
		judgeRoot();
		try {
			hdfsUtils.createNewFile(hdfsUtils.getFileSystem(), fileName, isroot);
		} catch (Exception e) {
			log.error("mkfile failed. [fileName:"+fileName+"]["+e.getMessage()+"]");
			errorMsg ="mkfile failed. [fileName:"+fileName+"]["+e.getMessage()+"]"; 
			return ERROR;
		}
		log.info("mkfile success. [fileName:"+fileName+"]");
		return SUCCESS;
	}
	public String deletefile(){
		judgeRoot();
		try {
			hdfsUtils.deleteFile(hdfsUtils.getFileSystem(), fileName, isroot);
		} catch (Exception e) {
			log.error("delete file failed. [fileName:"+fileName+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "delete file failed. [fileName:"+fileName+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("delete file success. [fileName:"+fileName+"]");
		return SUCCESS;
	}
	public String uploadfile(){
		judgeRoot();
		String tempFilePath = null;
		try {
			//tempFilePath = getSavePath()+"/"+getUploadFileName();
			tempFilePath = ServletActionContext.getServletContext().getRealPath("/")+"/upload/"+getUploadFileName();
			FileOutputStream fos = new FileOutputStream(tempFilePath);
			FileInputStream fis = new FileInputStream(getUpload());
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len=fis.read(buffer))>0){
				fos.write(buffer, 0, len);
			}
			hdfsUtils.upload(hdfsUtils.getFileSystem(), tempFilePath, remotepath,true,isroot);
		} catch (Exception e) {
		
			log.error("upload file failed. [tempFilePath:"+tempFilePath+"][remotepath"+remotepath+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "upload file failed. [tempFilePath:"+tempFilePath+"][remotepath"+remotepath+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("upload file success. [tempFilePath:"+tempFilePath+"][remotepath"+remotepath+"]");
		return SUCCESS;
	}
	
	public InputStream getDownloadfile(){
		judgeRoot();
		String tempFilePath = null;
		try {
			String remoteFileNameString = new File(remotepath).getName();
			tempFilePath = ServletActionContext.getServletContext().getRealPath("/")+"/download/"+remoteFileNameString;
			setRemoteFileName(remoteFileNameString);
			
			log.info("remote file name:"+remoteFileNameString);
			hdfsUtils.download(hdfsUtils.getFileSystem(), tempFilePath, remotepath, isroot);
			log.info("download file success. [tempFilePath:"+tempFilePath+"][rmotepath:"+remotepath+"]");
			return new FileInputStream(tempFilePath);
		} catch (Exception e) {
			log.info("download file failed. [tempFilePath:"+tempFilePath+"][rmotepath:"+remotepath+"][exception msg:"+remotepath+"]");
			errorMsg = "download file failed. [tempFilePath:"+tempFilePath+"][rmotepath:"+remotepath+"][exception msg:"+remotepath+"]";
			return null;
		}
	}

	public String renamefile(){
		judgeRoot();
		try {
			hdfsUtils.rename(hdfsUtils.getFileSystem(), oldName, newName, isroot);
		} catch (Exception e) {
			log.info("rename file failed. [oldNae:"+oldName+"][newName:"+newName+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "rename file failed. [oldNae:"+oldName+"][newName:"+newName+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("rename file success. [oldName:"+oldName+"][newName:"+newName+"]");
		return SUCCESS;
	}
	
	public String listFileStatus(){
		judgeRoot();
		try {
			fileStatusArray = hdfsUtils.listFileStatus(hdfsUtils.getFileSystem(), listfilepath, isroot);
		} catch (Exception e) {
			log.error(e.getMessage());
			errorMsg = e.getMessage();
			return ERROR;
		}
		return SUCCESS;
	}

}
