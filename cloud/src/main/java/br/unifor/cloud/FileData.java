package br.unifor.cloud;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.s3.model.S3ObjectSummary;

public class FileData {

	private String name;

	public FileData(String name) {
		this.name = name;
	}

	public static FileData fromSummary(S3ObjectSummary summary) {
		FileData data = new FileData(summary.getKey());
		return data;
	}

	public static List<FileData> fromSummaries(List<S3ObjectSummary> summaries) {
		List<FileData> data = new ArrayList<FileData>();
		for (S3ObjectSummary s : summaries) {
			data.add(new FileData(s.getKey()));
		}
		return data;
	}

	@Override
	public String toString() {
		return "File: " + name;
	}
	
	public String getName() {
		return name;
	}

}
