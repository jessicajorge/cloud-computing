package br.unifor.cloud;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.ec2.model.Instance;

public class InstanceData {

	private String id;
	private String status;

	public static InstanceData fromInstance(Instance instance) {
		InstanceData data = new InstanceData();
		data.id = instance.getInstanceId();
		data.status = instance.getState().getName();
		return data;
	}

	public static List<InstanceData> fromInstances(List<Instance> instances) {
		List<InstanceData> list = new ArrayList<InstanceData>();
		for (Instance instance : instances) {
			InstanceData data = new InstanceData();
			data.id = instance.getInstanceId();
			data.status = instance.getState().getName();
			list.add(data);
		}
		return list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ID: " + id + "  	  | Status: " + status;
	}

}
