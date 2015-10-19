package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.util.TransformUtil;

public class BootstrapMethods extends AttributeInfo {
	private byte[] num_bootstrap_methods;
	private List<BootstrapMethod> bootstrap_methods;

	public byte[] getNum_bootstrap_methods() {
		return num_bootstrap_methods;
	}

	public void setNum_bootstrap_methods(byte[] num_bootstrap_methods) {
		this.num_bootstrap_methods = num_bootstrap_methods;
	}

	public List<BootstrapMethod> getBootstrap_methods() {
		return bootstrap_methods;
	}

	public void setBootstrap_methods(List<BootstrapMethod> bootstrap_methods) {
		this.bootstrap_methods = bootstrap_methods;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		byte[] info = attributeInfo.getInfo();
		
		int[] index = new int[]{0};
		setNum_bootstrap_methods(TransformUtil.subBytes(info, index, 2));
		
		int num = TransformUtil.bytesToInt(getNum_bootstrap_methods());
		if (num > 0) {
			List<BootstrapMethod> bmethods = new ArrayList<BootstrapMethod>();
			for (int i = 0; i < num; i++) {
				BootstrapMethod bmethod = new BootstrapMethod();
				bmethod.setBootstrap_method_ref(TransformUtil.subBytes(info, index, 2));
				bmethod.setNum_bootstrap_arguments(TransformUtil.subBytes(info, index, 2));
				
				int arg_num = TransformUtil.bytesToInt(bmethod.getNum_bootstrap_arguments());
				if (arg_num > 0) {
					List<byte[]> barguments = new ArrayList<byte[]>();
					for (int j = 0; j < arg_num; j++) {
						barguments.add(TransformUtil.subBytes(info, index, 2));
					}
					bmethod.setBootstrap_arguments(barguments);
				}
			}
			setBootstrap_methods(bmethods);
		}
	}

	class BootstrapMethod {
		private byte[] bootstrap_method_ref;
		private byte[] num_bootstrap_arguments;
		private List<byte[]> bootstrap_arguments;

		public byte[] getBootstrap_method_ref() {
			return bootstrap_method_ref;
		}

		public void setBootstrap_method_ref(byte[] bootstrap_method_ref) {
			this.bootstrap_method_ref = bootstrap_method_ref;
		}

		public byte[] getNum_bootstrap_arguments() {
			return num_bootstrap_arguments;
		}

		public void setNum_bootstrap_arguments(byte[] num_bootstrap_arguments) {
			this.num_bootstrap_arguments = num_bootstrap_arguments;
		}

		public List<byte[]> getBootstrap_arguments() {
			return bootstrap_arguments;
		}

		public void setBootstrap_arguments(List<byte[]> bootstrap_arguments) {
			this.bootstrap_arguments = bootstrap_arguments;
		}

	}
}
