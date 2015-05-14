package com.onet.note.dto;

import java.util.List;

public class DirDTO {
	private Long id;
	private Long userId;
	private String name;
	private int noteCount;
	private Long pid;
	private String path; //保存路径，重命名时要同步更新
	private int depth;
	private boolean leaf;
	private List<DirDTO> children;
	public List<DirDTO> getChildren() {
		return children;
	}
	public void setChildren(List<DirDTO> children) {
		this.children = children;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public DirDTO() {
	}
	public DirDTO(Long userId, String name) {
		this.userId = userId;
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNoteCount() {
		return noteCount;
	}
	public void setNoteCount(int noteCount) {
		this.noteCount = noteCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
