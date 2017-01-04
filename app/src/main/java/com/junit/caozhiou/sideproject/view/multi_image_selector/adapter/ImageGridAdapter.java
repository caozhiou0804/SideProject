package com.junit.caozhiou.sideproject.view.multi_image_selector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.view.multi_image_selector.bean.Image;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片Adapter Created by Nereo on 2015/4/7.
 */
public class ImageGridAdapter extends BaseAdapter {

	private static final int TYPE_CAMERA = 0;
	private static final int TYPE_NORMAL = 1;

	private Context mContext;

	private LayoutInflater mInflater;
	private boolean showCamera = true;
	private boolean showSelectIndicator = true;

	private List<Image> mImages = new ArrayList<Image>();
	private List<Image> mSelectedImages = new ArrayList<Image>();

	private int mItemSize;
	private GridView.LayoutParams mItemLayoutParams;

	public ImageGridAdapter(Context context, boolean showCamera) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.showCamera = showCamera;
		mItemLayoutParams = new GridView.LayoutParams(
				GridView.LayoutParams.MATCH_PARENT,
				GridView.LayoutParams.MATCH_PARENT);
	}

	/**
	 * 显示选择指示器
	 * 
	 * @param b
	 */
	public void showSelectIndicator(boolean b) {
		showSelectIndicator = b;
	}

	public void setShowCamera(boolean b) {
		if (showCamera == b)
			return;

		showCamera = b;
		notifyDataSetChanged();
	}

	public boolean isShowCamera() {
		return showCamera;
	}

	/**
	 * 选择某个图片，改变选择状态
	 * 
	 * @param image
	 */
	public void select(Image image) {
		if (mSelectedImages.contains(image)) {
			mSelectedImages.remove(image);
		} else {
			mSelectedImages.add(image);
		}
		notifyDataSetChanged();
	}

	/**
	 * 通过图片路径设置默认选择
	 * 
	 * @param resultList
	 */
	public void setDefaultSelected(ArrayList<String> resultList) {
		for (String path : resultList) {
			Image image = getImageByPath(path);
			if (image != null) {
				mSelectedImages.add(image);
			}
		}
		if (mSelectedImages.size() > 0) {
			notifyDataSetChanged();
		}
	}

	private Image getImageByPath(String path) {
		if (mImages != null && mImages.size() > 0) {
			for (Image image : mImages) {
				if (image.path.equalsIgnoreCase(path)) {
					return image;
				}
			}
		}
		return null;
	}

	/**
	 * 设置数据集
	 * 
	 * @param images
	 */
	public void setData(List<Image> images) {
		mSelectedImages.clear();

		if (images != null && images.size() > 0) {
			mImages = images;
		} else {
			mImages.clear();
		}
		notifyDataSetChanged();
	}

	/**
	 * 重置每个Column的Size
	 * 
	 * @param columnWidth
	 */
	public void setItemSize(int columnWidth) {

		if (mItemSize == columnWidth) {
			return;
		}

		mItemSize = columnWidth;

		mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);

		notifyDataSetChanged();
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (showCamera) {
			return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
		}
		return TYPE_NORMAL;
	}

	@Override
	public int getCount() {
		return showCamera ? mImages.size() + 1 : mImages.size();
	}

	@Override
	public Image getItem(int i) {
		if (showCamera) {
			if (i == 0) {
				return null;
			}
			return mImages.get(i - 1);
		} else {
			return mImages.get(i);
		}
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		int type = getItemViewType(i);
		if (type == TYPE_CAMERA) {
			view = mInflater.inflate(R.layout.list_item_camera, viewGroup,
					false);
			view.setTag(null);
		} else if (type == TYPE_NORMAL) {
			ViewHolde holde;
			if (view == null) {
				view = mInflater.inflate(R.layout.list_item_image, viewGroup,
						false);
				holde = new ViewHolde(view);
			} else {
				holde = (ViewHolde) view.getTag();
				if (holde == null) {
					view = mInflater.inflate(R.layout.list_item_image,
							viewGroup, false);
					holde = new ViewHolde(view);
				}
			}
			if (holde != null) {
				holde.bindData(getItem(i));
			}
		}

		/** Fixed View Size */
		GridView.LayoutParams lp = (GridView.LayoutParams) view
				.getLayoutParams();
		if (lp.height != mItemSize) {
			view.setLayoutParams(mItemLayoutParams);
		}

		return view;
	}

	class ViewHolde {
		ImageView image;
		ImageView indicator;
		View mask;

		ViewHolde(View view) {
			image = (ImageView) view.findViewById(R.id.image);
			indicator = (ImageView) view.findViewById(R.id.checkmark);
			mask = view.findViewById(R.id.mask);
			view.setTag(this);
		}

		void bindData(final Image data) {
			if (data == null)
				return;
			// 处理单选和多选状态
			if (showSelectIndicator) {
				indicator.setVisibility(View.VISIBLE);
				if (mSelectedImages.contains(data)) {
					// 设置选中状态
					indicator.setImageResource(R.drawable.btn_selected);
					mask.setVisibility(View.VISIBLE);
				} else {
					// 未选择
					indicator.setImageResource(R.drawable.btn_unselected);
					mask.setVisibility(View.GONE);
				}
			} else {
				indicator.setVisibility(View.GONE);
			}
			File imageFile = new File(data.path);

			if (mItemSize > 0) {
				// 显示图片
				Picasso.with(mContext).load(imageFile)
						.placeholder(R.drawable.default_error)
						// .error(R.drawable.default_error)
						.resize(mItemSize, mItemSize).centerCrop().into(image);
			}
		}
	}


//	/**
//	 * 上传图片
//	 *
//	 * @param filepath
//	 */
//	private void uploadFile(final String filepath) {
//		// PubUtils.showTipDialog(UploadPhotoActivity.this, "图片上传中...");
//		String fileMimeType = PubUtils.getImgRealMimeType(PubUtils
//				.getFileFormat(filepath));
//		byte[] imgBytes = ImageUtils.File2Bytes(filepath);
////	if (null == VehicleApp.getInstance().getUserBean()
////		|| TextUtils.isEmpty(VehicleApp.getInstance().getUserBean()
////			.getDriverId())) {
////	    launch(LoginActivity.class);
////	    return;
////	}
//		HashMap<String, String> pram = new HashMap<String, String>();
//		if (imgBytes == null) {
//			return;
//		}
//		if (imgBytes.length < 9) {
//			return;
//		}
//		// image/png
//		final Request<FilePicBean> req = new Request<FilePicBean>();
//		// req.setUrl("http://192.168.111.83:8080/oim-ofs/uploadfile");
//		req.setUrl(Constant.FILE_UPLODE_URL + "uploadfile");
//		req.setRequestMethod(Request.M_POST); // 与POST 新增的区别，在这里改下即可
//		req.setRequestContenType(Request.RCT_FORMDATA);
//		req.setRequestParams(pram);
//		req.setSingleFileDateByte(imgBytes, fileMimeType);
//		req.setBaseParser(new BaseParser<FilePicBean>() {
//			@Override
//			public FilePicBean parseResDate(String resBody)
//					throws DataException {
//				if (resBody != null && !resBody.equals("")) {
//					try {
//
//						return JSON.parseObject(resBody, FilePicBean.class);
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//
//				}
//				return null;
//			}
//		});
//
//		Action action = new Action(UploadPhotoActivity.this);
//		action.setDefaultLoadingTipOpen(false);
//		action.execute(req, new OnResponseListener<FilePicBean>() {
//
//			@Override
//			public void onResponseFinished(Request<FilePicBean> request,
//										   Response<FilePicBean> response) {
//				uploadFileIndex++;
//				FilePicBean bean = new FilePicBean();
//				bean = response.getT();
//				if (null != bean) {
//					if (bean.getError().equals("0")) {
//						L.e("yinzl", "uploadFileIndex url is :"
//								+ uploadFileIndex);
//						if (uploadFileIndex <= mSelectPathTemp.size()) {
//							bean.setFile_sdPath(filepath);
//							bean.setPhotoType(upload_label);
//							list_filepic.add(bean);
//
//						}
//						if (uploadFileIndex < mSelectPathTemp.size()) {
//							uploadFile(mSelectPathTemp.get(uploadFileIndex));
//						} else {
//							ImageUtils.deleteDir(Environment
//									.getExternalStorageDirectory()
//									.getAbsolutePath()
//									+ ImageUtils.SDSAVEPATH);
//							L.e("yinzl", "file url is :" + bean.toString());
//							new Thread(new Runnable() {
//
//								@Override
//								public void run() {
//									// 推送一个事件
//									FileUploadEvent appevent = new FileUploadEvent(
//											upload_label);
//									appevent.setList_file(list_filepic);
//									appevent.setFilePath_zheng(str_filepath1);
//									appevent.setFilePath_fan(str_filepath2);
//									EventBus.getDefault().post(appevent);
//									finish();
//								}
//							}).start();
//
//						}
//					}
//					MyToast.showShort(UploadPhotoActivity.this,
//							bean.getRetRemark());
//				}
//				ProgeressUtils.closeLoadingDialogMy();
//			}
//
//			@Override
//			public void onResponseDataError(Request<FilePicBean> equest) {
//				// TODO Auto-generated method stub
//				ProgeressUtils.closeLoadingDialogMy();
//			}
//
//			@Override
//			public void onResponseConnectionError(Request<FilePicBean> request,
//												  int statusCode) {
//				ProgeressUtils.closeLoadingDialogMy();
//			}
//
//			@Override
//			public void onResponseFzzError(Request<FilePicBean> request,
//										   ErrorMsg errorMsg) {
//				ProgeressUtils.closeLoadingDialogMy();
//			}
//		});
//	}

}
