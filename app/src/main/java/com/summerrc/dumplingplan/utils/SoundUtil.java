package com.summerrc.dumplingplan.utils;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.summerrc.dumplingplan.R;

public class SoundUtil  {
	/** 音效编号 */
	public static final int XUANZHONG=1;			//棋子选中声音编号
	public static final int CHIZI=2;							//吃子声音编号
	public static final int PUTONGLUOZI=3;		//普通落子声音编号

	public static SoundPool soundPool;				//声音缓冲池
	public static HashMap<Integer, Integer> soundPoolMap; 				//存放声音ID的Map

	/**
	 * *声音缓冲池的初始化
	 * @param context 上下文参数
	 */
	public static void initSounds(Context context) {
		/** 创建声音缓冲池 */
		soundPool = new SoundPool(
						2, 							//同时能最多播放的个数
						AudioManager.STREAM_MUSIC,     //音频的类型
						100							//声音的播放质量，目前无效
		);


		soundPoolMap = new HashMap<Integer, Integer>();			//创建声音资源Map
		/** 将加载的声音资源id放进此Map */
		soundPoolMap.put(XUANZHONG, soundPool.load(context, R.raw.xuanzhong, 1));
		soundPoolMap.put(CHIZI, soundPool.load(context, R.raw.chizi,1));
		soundPoolMap.put(PUTONGLUOZI, soundPool.load(context, R.raw.putongluozi,1));
	}

	/**
	 * 播放声音的方法
	 * @param key  存放音频资源id的HashMap的key
	 * @param loop 是否循环
	 * @param context 上下文参数
	 */
	public static void playSounds(int key, int loop, Context context) {
		AudioManager mgr = (AudioManager)  context.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		soundPool.play(
				soundPoolMap.get(key), 		//声音id
				volume, 									//左声道
				volume, 									//右声道
				1,												 //优先级
				loop,										//是否循环
				0.5f											 //rate
		);
	}
}
