package com.nekokittygames.thaumictinkerer.client;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.nekokittygames.thaumictinkerer.common.items.Kami.ItemIchoriumFortressArmor;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.models.gear.ModelCustomArmor;

import java.util.HashMap;

public class ModelIchoriumFortressArmor extends ModelCustomArmor {
	private final ModelRenderer helm;
	private final ModelRenderer yelv_;
	private final ModelRenderer helm2;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r3_r1;
	private final ModelRenderer root;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer r5_2;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r7_r1;
	private final ModelRenderer cube_r7_2;
	private final ModelRenderer cube_r7_r2;
	private final ModelRenderer cube_r8_r1;
	private final ModelRenderer cube_r7_r3;
	private final ModelRenderer cube_r7_3;
	private final ModelRenderer cube_r7_r4;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r8_r2;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;
	private final ModelRenderer cube_r11;
	private final ModelRenderer Cuirass;
	private final ModelRenderer body;
	private final ModelRenderer cube_r12;
	private final ModelRenderer cube_r13;
	private final ModelRenderer book;
	private final ModelRenderer book_r1;
	private final ModelRenderer cube_r14;
	private final ModelRenderer yao_dai;
	private final ModelRenderer yao_dai_r1;
	private final ModelRenderer LeftArm;
	private final ModelRenderer left_jianjie;
	private final ModelRenderer cube_r15;
	private final ModelRenderer cube_r16;
	private final ModelRenderer cube_r17;
	private final ModelRenderer left_bijia;
	private final ModelRenderer RightArm;
	private final ModelRenderer right_jianjia;
	private final ModelRenderer cube_r18;
	private final ModelRenderer cube_r19;
	private final ModelRenderer cube_r20;
	private final ModelRenderer right_bijia;
	private final ModelRenderer RightLeg;
	private final ModelRenderer right_leg_armor;
	private final ModelRenderer cebian_zhuangjia2;
	private final ModelRenderer cube_r24;
	private final ModelRenderer cube_r24_2;
	private final ModelRenderer cube_r24_3;
	private final ModelRenderer zhengmian_zhuangjia;
	private final ModelRenderer cube_r25;
	private final ModelRenderer houfang_zhuangjia;
	private final ModelRenderer cube_r26;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer left_leg_armor;
	private final ModelRenderer cebian_zhuangjia;
	private final ModelRenderer cube_r21;
	private final ModelRenderer cube_r21_2;
	private final ModelRenderer cube_r21_3;
	private final ModelRenderer zhengmian_zhuangjia2;
	private final ModelRenderer cube_r22;
	private final ModelRenderer houfang_zhuangjia2;
	private final ModelRenderer cube_r23;
	private static HashMap<Integer, Integer> hasSet;

	public ModelIchoriumFortressArmor(float f) {
		super(f, 0, 128, 64);
		textureWidth = 128;
		textureHeight = 64;

		helm = new ModelRenderer(this);
		helm.setRotationPoint(0.0F, 0.0F, 0.0F);


		yelv_ = new ModelRenderer(this);
		yelv_.setRotationPoint(0.0F, 4.0F, 0.0F);
		helm.addChild(yelv_);


		helm2 = new ModelRenderer(this);
		helm2.setRotationPoint(0.0F, 0.0F, 0.0F);
		yelv_.addChild(helm2);
		helm2.cubeList.add(new ModelBox(helm2, 0, 0, -4.5F, -14.0F, -4.5F, 9, 3, 9, 0.0F, false));
		helm2.cubeList.add(new ModelBox(helm2, 0, 14, -4.5F, -7.0F, -5.0F, 4, 4, 1, 0.0F, false));
		helm2.cubeList.add(new ModelBox(helm2, 0, 18, -4.5F, -12.0F, -5.0F, 4, 4, 1, 0.0F, false));
		helm2.cubeList.add(new ModelBox(helm2, 0, 14, 0.5F, -7.0F, -5.0F, 4, 4, 1, 0.0F, true));
		helm2.cubeList.add(new ModelBox(helm2, 0, 18, 0.5F, -12.0F, -5.0F, 4, 4, 1, 0.0F, true));
		helm2.cubeList.add(new ModelBox(helm2, 10, 13, -4.0F, -13.0F, 4.0F, 8, 9, 1, 0.0F, false));
		helm2.cubeList.add(new ModelBox(helm2, 0, 33, -5.0F, -13.0F, -4.0F, 1, 9, 9, 0.0F, false));
		helm2.cubeList.add(new ModelBox(helm2, 0, 33, 4.0F, -13.0F, -4.0F, 1, 9, 9, 0.0F, true));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(5.0F, -9.0F, -2.0F);
		helm2.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.5236F, 0.5236F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 19, 34, -2.3F, -2.0F, 0.1F, 2, 2, 7, 0.0F, true));
		cube_r1.cubeList.add(new ModelBox(cube_r1, 19, 30, -2.4F, -0.1F, -0.3F, 2, 1, 4, 0.0F, true));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-5.0F, -9.0F, -2.0F);
		helm2.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.5236F, -0.5236F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 19, 30, 0.5F, 0.0F, 0.7F, 2, 1, 4, 0.0F, false));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 19, 34, 0.5F, -2.0F, 1.1F, 2, 2, 7, 0.0F, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, -14.0F, -2.5F);
		helm2.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.8727F, 0.0F, 0.0F);


		cube_r3_r1 = new ModelRenderer(this);
		cube_r3_r1.setRotationPoint(0.0F, 1.75F, 1.75F);
		cube_r3.addChild(cube_r3_r1);
		setRotationAngle(cube_r3_r1, -0.2182F, 0.0F, 0.0F);
		cube_r3_r1.cubeList.add(new ModelBox(cube_r3_r1, 0, 27, -1.0F, 0.95F, -1.95F, 2, 1, 5, 0.0F, false));
		cube_r3_r1.cubeList.add(new ModelBox(cube_r3_r1, 20, 43, -1.0F, -1.05F, -1.95F, 2, 2, 6, 0.0F, false));

		root = new ModelRenderer(this);
		root.setRotationPoint(0.0F, 0.0F, 0.0F);
		yelv_.addChild(root);


		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(-2.0F, -8.0F, 6.0F);
		root.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.1745F, 0.0F, 0.0F);


		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-2.0F, -12.0F, 7.0F);
		root.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.2618F, 0.0F, 0.0F);


		r5_2 = new ModelRenderer(this);
		r5_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		cube_r5.addChild(r5_2);


		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(3.5F, -13.0F, -4.0F);
		root.addChild(cube_r6);
		setRotationAngle(cube_r6, -1.3871F, 1.1265F, 0.5555F);


		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(3.5F, -13.0F, -4.0F);
		root.addChild(cube_r7);
		setRotationAngle(cube_r7, -0.8635F, 1.1265F, 0.5555F);


		cube_r8_r1 = new ModelRenderer(this);
		cube_r8_r1.setRotationPoint(-3.6791F, 6.4853F, -0.8134F);
		cube_r7.addChild(cube_r8_r1);
		setRotationAngle(cube_r8_r1, 0.6576F, 0.8466F, -1.0685F);
		cube_r8_r1.cubeList.add(new ModelBox(cube_r8_r1, 120, 58, -1.5184F, -3.2447F, -0.6675F, 2, 4, 2, 0.0F, true));

		cube_r7_2 = new ModelRenderer(this);
		cube_r7_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		cube_r7.addChild(cube_r7_2);


		cube_r7_r1 = new ModelRenderer(this);
		cube_r7_r1.setRotationPoint(-2.5185F, -2.6886F, 0.0799F);
		cube_r7_2.addChild(cube_r7_r1);
		setRotationAngle(cube_r7_r1, 0.2647F, 0.3821F, -0.8118F);
		cube_r7_r1.cubeList.add(new ModelBox(cube_r7_r1, 120, 53, -0.9758F, -2.9139F, -1.9169F, 2, 3, 2, 0.0F, true));

		cube_r7_r2 = new ModelRenderer(this);
		cube_r7_r2.setRotationPoint(-3.6791F, 6.4853F, -0.8134F);
		cube_r7_2.addChild(cube_r7_r2);
		setRotationAngle(cube_r7_r2, 0.197F, 0.7995F, -1.3271F);
		cube_r7_r2.cubeList.add(new ModelBox(cube_r7_r2, 120, 53, -1.0335F, -6.1124F, -1.5724F, 2, 3, 2, 0.0F, true));

		cube_r7_3 = new ModelRenderer(this);
		cube_r7_3.setRotationPoint(0.0F, 0.0F, 0.0F);
		cube_r7.addChild(cube_r7_3);


		cube_r7_r3 = new ModelRenderer(this);
		cube_r7_r3.setRotationPoint(-10.8722F, 5.9403F, -1.8991F);
		cube_r7_3.addChild(cube_r7_r3);
		setRotationAngle(cube_r7_r3, 0.0053F, -0.4728F, -1.3821F);
		cube_r7_r3.cubeList.add(new ModelBox(cube_r7_r3, 121, 54, -0.5F, -1.8F, -0.5F, 1, 3, 1, 0.0F, true));

		cube_r7_r4 = new ModelRenderer(this);
		cube_r7_r4.setRotationPoint(-5.9794F, -4.482F, -1.2811F);
		cube_r7_3.addChild(cube_r7_r4);
		setRotationAngle(cube_r7_r4, 0.0403F, 0.3083F, -1.0578F);
		cube_r7_r4.cubeList.add(new ModelBox(cube_r7_r4, 121, 54, -0.3F, -1.8F, -0.8F, 1, 3, 1, 0.0F, true));

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(3.5F, -13.0F, -4.0F);
		root.addChild(cube_r8);
		setRotationAngle(cube_r8, -0.3399F, 1.1265F, 0.5555F);


		cube_r8_r2 = new ModelRenderer(this);
		cube_r8_r2.setRotationPoint(0.0F, -0.4575F, 2.0245F);
		cube_r8.addChild(cube_r8_r2);
		setRotationAngle(cube_r8_r2, 0.1876F, 0.1219F, -0.6444F);
		cube_r8_r2.cubeList.add(new ModelBox(cube_r8_r2, 120, 58, -1.7931F, -3.3984F, -1.9234F, 2, 4, 2, 0.0F, true));

		cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(-3.5F, -13.0F, -4.0F);
		root.addChild(cube_r9);
		setRotationAngle(cube_r9, -1.3871F, -1.1265F, -0.5555F);


		cube_r10 = new ModelRenderer(this);
		cube_r10.setRotationPoint(-3.5F, -13.0F, -4.0F);
		root.addChild(cube_r10);
		setRotationAngle(cube_r10, -0.8635F, -1.1265F, -0.5555F);


		cube_r11 = new ModelRenderer(this);
		cube_r11.setRotationPoint(-3.5F, -13.0F, -4.0F);
		root.addChild(cube_r11);
		setRotationAngle(cube_r11, -0.3399F, -1.1265F, -0.5555F);


		Cuirass = new ModelRenderer(this);
		Cuirass.setRotationPoint(0.0F, 0.0F, 0.0F);


		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 16.0F, 0.0F);
		Cuirass.addChild(body);
		body.cubeList.add(new ModelBox(body, 36, 42, -4.0F, -15.0F, -4.0F, 8, 7, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 36, 51, -4.0F, -15.0F, 2.0F, 8, 11, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 114, 35, -5.0F, -13.0F, -3.0F, 1, 3, 6, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 114, 35, 4.0F, -13.0F, -3.0F, 1, 3, 6, 0.0F, true));
		body.cubeList.add(new ModelBox(body, 56, 46, -2.0F, -13.0F, -5.0F, 4, 4, 1, 0.0F, false));

		cube_r12 = new ModelRenderer(this);
		cube_r12.setRotationPoint(2.0F, -11.0F, -5.0F);
		body.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.0F, -0.5236F, 0.0F);
		cube_r12.cubeList.add(new ModelBox(cube_r12, 60, 43, 0.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F, true));

		cube_r13 = new ModelRenderer(this);
		cube_r13.setRotationPoint(-2.0F, -11.0F, -5.0F);
		body.addChild(cube_r13);
		setRotationAngle(cube_r13, 0.0F, 0.5236F, 0.0F);
		cube_r13.cubeList.add(new ModelBox(cube_r13, 60, 43, -2.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F, false));

		book = new ModelRenderer(this);
		book.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(book);


		book_r1 = new ModelRenderer(this);
		book_r1.setRotationPoint(0.0F, -10.5F, 5.0F);
		book.addChild(book_r1);
		setRotationAngle(book_r1, 0.0F, 0.0F, 0.7854F);
		book_r1.cubeList.add(new ModelBox(book_r1, 82, 49, -2.5F, -4.0F, -1.0F, 5, 7, 2, 0.0F, false));

		cube_r14 = new ModelRenderer(this);
		cube_r14.setRotationPoint(0.0F, -3.5F, 5.5F);
		book.addChild(cube_r14);
		setRotationAngle(cube_r14, 0.0F, 0.0F, 0.2618F);
		cube_r14.cubeList.add(new ModelBox(cube_r14, 74, 58, -4.0F, -2.0F, -1.5F, 8, 3, 3, 0.0F, false));

		yao_dai = new ModelRenderer(this);
		yao_dai.setRotationPoint(0.0F, 16.0F, 0.0F);
		Cuirass.addChild(yao_dai);
		yao_dai.cubeList.add(new ModelBox(yao_dai, 114, 35, -5.0F, -8.0F, -2.0F, 1, 3, 6, 0.0F, false));
		yao_dai.cubeList.add(new ModelBox(yao_dai, 110, 44, -4.0F, -8.0F, -3.0F, 8, 3, 1, 0.0F, false));
		yao_dai.cubeList.add(new ModelBox(yao_dai, 114, 35, 4.0F, -8.0F, -2.0F, 1, 3, 6, 0.0F, true));

		yao_dai_r1 = new ModelRenderer(this);
		yao_dai_r1.setRotationPoint(0.0F, -6.5F, 4.5F);
		yao_dai.addChild(yao_dai_r1);
		setRotationAngle(yao_dai_r1, 0.0F, 3.1416F, 0.0F);
		yao_dai_r1.cubeList.add(new ModelBox(yao_dai_r1, 110, 44, -4.0F, -1.5F, -0.5F, 8, 3, 1, 0.0F, false));

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);


		left_jianjie = new ModelRenderer(this);
		left_jianjie.setRotationPoint(1.0F, 9.5F, 0.0F);
		LeftArm.addChild(left_jianjie);


		cube_r15 = new ModelRenderer(this);
		cube_r15.setRotationPoint(0.0F, -12.0F, 0.0F);
		left_jianjie.addChild(cube_r15);
		setRotationAngle(cube_r15, 0.0F, 0.0F, 0.6109F);
		cube_r15.cubeList.add(new ModelBox(cube_r15, 70, 26, 4.0F, -0.5F, -3.0F, 2, 1, 6, 0.0F, true));
		cube_r15.cubeList.add(new ModelBox(cube_r15, 56, 26, 4.0F, -1.5F, -3.0F, 1, 1, 6, 0.0F, true));
		cube_r15.cubeList.add(new ModelBox(cube_r15, 56, 17, -2.0F, -2.5F, -3.0F, 6, 3, 6, 0.0F, true));

		cube_r16 = new ModelRenderer(this);
		cube_r16.setRotationPoint(0.0F, -12.0F, 0.0F);
		left_jianjie.addChild(cube_r16);
		setRotationAngle(cube_r16, -0.3491F, 0.0F, 0.4363F);
		cube_r16.cubeList.add(new ModelBox(cube_r16, 112, 58, 1.3169F, -5.0245F, 0.4451F, 2, 4, 2, 0.0F, true));

		cube_r17 = new ModelRenderer(this);
		cube_r17.setRotationPoint(0.0F, -12.0F, 0.0F);
		left_jianjie.addChild(cube_r17);
		setRotationAngle(cube_r17, 0.1745F, 0.0F, 0.4363F);
		cube_r17.cubeList.add(new ModelBox(cube_r17, 116, 54, 2.0F, -6.0F, 3.5F, 1, 3, 1, 0.0F, true));

		left_bijia = new ModelRenderer(this);
		left_bijia.setRotationPoint(-5.0F, 14.0F, 0.0F);
		LeftArm.addChild(left_bijia);
		left_bijia.cubeList.add(new ModelBox(left_bijia, 96, 46, 3.0F, -10.5F, -2.5F, 4, 1, 1, 0.0F, true));
		left_bijia.cubeList.add(new ModelBox(left_bijia, 36, 27, 7.0F, -10.5F, -2.5F, 2, 6, 5, 0.0F, true));
		left_bijia.cubeList.add(new ModelBox(left_bijia, 96, 46, 3.0F, -7.5F, -2.5F, 4, 1, 1, 0.0F, true));
		left_bijia.cubeList.add(new ModelBox(left_bijia, 96, 42, 3.0F, -7.5F, -1.5F, 1, 1, 3, 0.0F, true));
		left_bijia.cubeList.add(new ModelBox(left_bijia, 96, 42, 3.0F, -10.5F, -1.5F, 1, 1, 3, 0.0F, true));
		left_bijia.cubeList.add(new ModelBox(left_bijia, 96, 46, 3.0F, -10.5F, 1.5F, 4, 1, 1, 0.0F, true));
		left_bijia.cubeList.add(new ModelBox(left_bijia, 96, 46, 3.0F, -7.5F, 1.5F, 4, 1, 1, 0.0F, true));
		left_bijia.cubeList.add(new ModelBox(left_bijia, 36, 16, 3.5F, -17.0F, -2.5F, 5, 5, 5, 0.0F, true));

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);


		right_jianjia = new ModelRenderer(this);
		right_jianjia.setRotationPoint(-1.0F, 9.5F, 0.0F);
		RightArm.addChild(right_jianjia);


		cube_r18 = new ModelRenderer(this);
		cube_r18.setRotationPoint(0.0F, -12.0F, 0.0F);
		right_jianjia.addChild(cube_r18);
		setRotationAngle(cube_r18, 0.0F, 0.0F, -0.6109F);
		cube_r18.cubeList.add(new ModelBox(cube_r18, 70, 26, -6.0F, -0.5F, -3.0F, 2, 1, 6, 0.0F, false));
		cube_r18.cubeList.add(new ModelBox(cube_r18, 56, 26, -5.0F, -1.5F, -3.0F, 1, 1, 6, 0.0F, false));
		cube_r18.cubeList.add(new ModelBox(cube_r18, 56, 17, -4.0F, -2.5F, -3.0F, 6, 3, 6, 0.0F, false));

		cube_r19 = new ModelRenderer(this);
		cube_r19.setRotationPoint(0.0F, -12.0F, 0.0F);
		right_jianjia.addChild(cube_r19);
		setRotationAngle(cube_r19, -0.3491F, 0.0F, -0.4363F);
		cube_r19.cubeList.add(new ModelBox(cube_r19, 112, 58, -3.3169F, -5.0245F, 0.4451F, 2, 4, 2, 0.0F, false));

		cube_r20 = new ModelRenderer(this);
		cube_r20.setRotationPoint(0.0F, -12.0F, 0.0F);
		right_jianjia.addChild(cube_r20);
		setRotationAngle(cube_r20, 0.1745F, 0.0F, -0.4363F);
		cube_r20.cubeList.add(new ModelBox(cube_r20, 116, 54, -3.0F, -6.0F, 3.5F, 1, 3, 1, 0.0F, false));

		right_bijia = new ModelRenderer(this);
		right_bijia.setRotationPoint(5.0F, 14.0F, 0.0F);
		RightArm.addChild(right_bijia);
		right_bijia.cubeList.add(new ModelBox(right_bijia, 96, 46, -7.0F, -10.5F, 1.5F, 4, 1, 1, 0.0F, false));
		right_bijia.cubeList.add(new ModelBox(right_bijia, 96, 46, -7.0F, -7.5F, 1.5F, 4, 1, 1, 0.0F, false));
		right_bijia.cubeList.add(new ModelBox(right_bijia, 36, 27, -9.0F, -10.5F, -2.5F, 2, 6, 5, 0.0F, false));
		right_bijia.cubeList.add(new ModelBox(right_bijia, 96, 46, -7.0F, -10.5F, -2.5F, 4, 1, 1, 0.0F, false));
		right_bijia.cubeList.add(new ModelBox(right_bijia, 96, 46, -7.0F, -7.5F, -2.5F, 4, 1, 1, 0.0F, false));
		right_bijia.cubeList.add(new ModelBox(right_bijia, 96, 42, -4.0F, -10.5F, -1.5F, 1, 1, 3, 0.0F, false));
		right_bijia.cubeList.add(new ModelBox(right_bijia, 96, 42, -4.0F, -7.5F, -1.5F, 1, 1, 3, 0.0F, false));
		right_bijia.cubeList.add(new ModelBox(right_bijia, 36, 16, -8.5F, -17.0F, -2.5F, 5, 5, 5, 0.0F, false));

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);


		right_leg_armor = new ModelRenderer(this);
		right_leg_armor.setRotationPoint(1.9F, 4.0F, 0.0F);
		RightLeg.addChild(right_leg_armor);


		cebian_zhuangjia2 = new ModelRenderer(this);
		cebian_zhuangjia2.setRotationPoint(-4.5F, 9.0F, 0.0F);
		right_leg_armor.addChild(cebian_zhuangjia2);


		cube_r24 = new ModelRenderer(this);
		cube_r24.setRotationPoint(0.0F, -12.0F, 0.0F);
		cebian_zhuangjia2.addChild(cube_r24);
		setRotationAngle(cube_r24, 0.0F, 0.0F, 0.4363F);
		cube_r24.cubeList.add(new ModelBox(cube_r24, 12, 55, -0.5F, -1.0F, -2.5F, 1, 4, 5, 0.0F, false));

		cube_r24_2 = new ModelRenderer(this);
		cube_r24_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		cube_r24.addChild(cube_r24_2);
		cube_r24_2.cubeList.add(new ModelBox(cube_r24_2, 12, 55, 0.5F, 1.0F, -2.5F, 1, 4, 5, 0.0F, false));

		cube_r24_3 = new ModelRenderer(this);
		cube_r24_3.setRotationPoint(0.0F, 0.0F, 0.0F);
		cube_r24.addChild(cube_r24_3);
		cube_r24_3.cubeList.add(new ModelBox(cube_r24_3, 12, 55, 1.5F, 3.0F, -2.5F, 1, 4, 5, 0.0F, false));

		zhengmian_zhuangjia = new ModelRenderer(this);
		zhengmian_zhuangjia.setRotationPoint(-2.75F, 7.75F, -3.5F);
		right_leg_armor.addChild(zhengmian_zhuangjia);


		cube_r25 = new ModelRenderer(this);
		cube_r25.setRotationPoint(0.0F, -12.0F, 0.0F);
		zhengmian_zhuangjia.addChild(cube_r25);
		setRotationAngle(cube_r25, -0.5236F, 0.0F, 0.0F);
		cube_r25.cubeList.add(new ModelBox(cube_r25, 4, 55, -0.25F, 3.25F, 2.5F, 3, 4, 1, 0.0F, false));
		cube_r25.cubeList.add(new ModelBox(cube_r25, 6, 51, -2.25F, 3.25F, 2.5F, 2, 3, 1, 0.0F, false));
		cube_r25.cubeList.add(new ModelBox(cube_r25, 4, 55, -0.25F, 1.25F, 1.5F, 3, 4, 1, 0.0F, false));
		cube_r25.cubeList.add(new ModelBox(cube_r25, 4, 55, -0.25F, -0.75F, 0.5F, 3, 4, 1, 0.0F, false));
		cube_r25.cubeList.add(new ModelBox(cube_r25, 6, 51, -2.25F, 1.25F, 1.5F, 2, 3, 1, 0.0F, false));
		cube_r25.cubeList.add(new ModelBox(cube_r25, 6, 51, -2.25F, -0.75F, 0.5F, 2, 3, 1, 0.0F, false));

		houfang_zhuangjia = new ModelRenderer(this);
		houfang_zhuangjia.setRotationPoint(-2.5F, 8.5F, 3.0F);
		right_leg_armor.addChild(houfang_zhuangjia);


		cube_r26 = new ModelRenderer(this);
		cube_r26.setRotationPoint(0.0F, -12.0F, 0.0F);
		houfang_zhuangjia.addChild(cube_r26);
		setRotationAngle(cube_r26, 0.4363F, 0.0F, 0.0F);
		cube_r26.cubeList.add(new ModelBox(cube_r26, 0, 60, -2.5F, -0.5F, -0.5F, 5, 3, 1, 0.0F, false));
		cube_r26.cubeList.add(new ModelBox(cube_r26, 0, 60, -2.5F, 1.5F, -1.5F, 5, 3, 1, 0.0F, false));
		cube_r26.cubeList.add(new ModelBox(cube_r26, 0, 60, -2.5F, 3.5F, -2.5F, 5, 3, 1, 0.0F, false));

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);


		left_leg_armor = new ModelRenderer(this);
		left_leg_armor.setRotationPoint(-1.9F, 4.0F, 0.0F);
		LeftLeg.addChild(left_leg_armor);


		cebian_zhuangjia = new ModelRenderer(this);
		cebian_zhuangjia.setRotationPoint(4.5F, 9.0F, 0.0F);
		left_leg_armor.addChild(cebian_zhuangjia);


		cube_r21 = new ModelRenderer(this);
		cube_r21.setRotationPoint(0.0F, -12.0F, 0.0F);
		cebian_zhuangjia.addChild(cube_r21);
		setRotationAngle(cube_r21, 0.0F, 0.0F, -0.4363F);
		cube_r21.cubeList.add(new ModelBox(cube_r21, 12, 55, -0.5F, -1.0F, -2.5F, 1, 4, 5, 0.0F, true));

		cube_r21_2 = new ModelRenderer(this);
		cube_r21_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		cube_r21.addChild(cube_r21_2);
		cube_r21_2.cubeList.add(new ModelBox(cube_r21_2, 12, 55, -1.5F, 1.0F, -2.5F, 1, 4, 5, 0.0F, true));

		cube_r21_3 = new ModelRenderer(this);
		cube_r21_3.setRotationPoint(0.0F, 12.0F, 0.0F);
		cube_r21.addChild(cube_r21_3);
		cube_r21_3.cubeList.add(new ModelBox(cube_r21_3, 12, 55, -2.5F, -9.0F, -2.5F, 1, 4, 5, 0.0F, true));

		zhengmian_zhuangjia2 = new ModelRenderer(this);
		zhengmian_zhuangjia2.setRotationPoint(2.75F, 7.75F, -3.5F);
		left_leg_armor.addChild(zhengmian_zhuangjia2);


		cube_r22 = new ModelRenderer(this);
		cube_r22.setRotationPoint(0.0F, -12.0F, 0.0F);
		zhengmian_zhuangjia2.addChild(cube_r22);
		setRotationAngle(cube_r22, -0.5236F, 0.0F, 0.0F);
		cube_r22.cubeList.add(new ModelBox(cube_r22, 4, 55, -2.75F, 1.25F, 1.5F, 3, 4, 1, 0.0F, true));
		cube_r22.cubeList.add(new ModelBox(cube_r22, 6, 51, 0.25F, 1.25F, 1.5F, 2, 3, 1, 0.0F, true));
		cube_r22.cubeList.add(new ModelBox(cube_r22, 4, 55, -2.75F, -0.75F, 0.5F, 3, 4, 1, 0.0F, true));
		cube_r22.cubeList.add(new ModelBox(cube_r22, 6, 51, 0.25F, -0.75F, 0.5F, 2, 3, 1, 0.0F, true));
		cube_r22.cubeList.add(new ModelBox(cube_r22, 6, 51, 0.25F, 3.25F, 2.5F, 2, 3, 1, 0.0F, true));
		cube_r22.cubeList.add(new ModelBox(cube_r22, 4, 55, -2.75F, 3.25F, 2.5F, 3, 4, 1, 0.0F, true));

		houfang_zhuangjia2 = new ModelRenderer(this);
		houfang_zhuangjia2.setRotationPoint(2.5F, 8.5F, 3.0F);
		left_leg_armor.addChild(houfang_zhuangjia2);


		cube_r23 = new ModelRenderer(this);
		cube_r23.setRotationPoint(0.0F, -12.0F, 0.0F);
		houfang_zhuangjia2.addChild(cube_r23);
		setRotationAngle(cube_r23, 0.4363F, 0.0F, 0.0F);
		cube_r23.cubeList.add(new ModelBox(cube_r23, 0, 60, -2.5F, 1.5F, -1.5F, 5, 3, 1, 0.0F, true));
		cube_r23.cubeList.add(new ModelBox(cube_r23, 0, 60, -2.5F, -0.5F, -0.5F, 5, 3, 1, 0.0F, true));
		cube_r23.cubeList.add(new ModelBox(cube_r23, 0, 60, -2.5F, 3.5F, -2.5F, 5, 3, 1, 0.0F, true));

		bipedHeadwear.cubeList.clear();
		bipedHead.cubeList.clear();
		bipedHead.addChild(helm);
		bipedBody.cubeList.clear();
		if (f < 1.0f) {
			bipedBody.addChild(yao_dai);
		}
		else {
			bipedBody.addChild(body);
		}
		bipedRightArm.cubeList.clear();
		bipedRightArm.addChild(right_jianjia);
		bipedRightArm.addChild(right_bijia);
		bipedLeftArm.cubeList.clear();
		bipedLeftArm.addChild(left_jianjie);
		bipedLeftArm.addChild(left_bijia);
		bipedRightLeg.cubeList.clear();
		bipedRightLeg.addChild(right_leg_armor);
		bipedLeftLeg.cubeList.clear();
		bipedLeftLeg.addChild(left_leg_armor);
	}

	private void checkSet(Entity entity) {
		if (entity instanceof EntityLivingBase && entity.ticksExisted % 20 == 0) {
			int set = 0;
			for (int a = 2; a < 5; ++a) {
				ItemStack piece = ((EntityLivingBase)entity).getItemStackFromSlot(EntityEquipmentSlot.values()[a + 1]);
				if (piece != null && piece.getItem() instanceof ItemIchoriumFortressArmor) {
					++set;
					//if (a == 4) {
					//	if (piece.hasTagCompound() && piece.getTagCompound().hasKey("mask")) {
					//		ModelChlorophyteArmor.hasMask.put(entity.getEntityId(), piece.getTagCompound().getInteger("mask"));
					//	}
					//	else {
					//		ModelChlorophyteArmor.hasMask.remove(entity.getEntityId());
					//	}
					//	if (piece.hasTagCompound() && piece.getTagCompound().hasKey("goggles")) {
					//		ModelChlorophyteArmor.hasGoggles.put(entity.getEntityId(), true);
					//	}
					//	else {
					//		ModelChlorophyteArmor.hasGoggles.remove(entity.getEntityId());
					//	}
					//}
				}
			}
			if (set > 0) {
				ModelIchoriumFortressArmor.hasSet.put(entity.getEntityId(), set);
			}
			else {
				ModelIchoriumFortressArmor.hasSet.remove(entity.getEntityId());
			}
		}
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		checkSet(entity);
		int set = ModelIchoriumFortressArmor.hasSet.containsKey(entity.getEntityId()) ? ModelIchoriumFortressArmor.hasSet.get(entity.getEntityId()) : -1;
		cube_r14.isHidden = (set < 3);
		book_r1.isHidden = (set < 2);
		cube_r7_2.isHidden = (set < 3);
		cube_r7_3.isHidden = (set < 3);
		cube_r15.isHidden = (set < 2);
		cube_r18.isHidden = (set < 2);
		cube_r16.isHidden = (set < 3);
		cube_r17.isHidden = (set < 3);
		cube_r19.isHidden = (set < 3);
		cube_r20.isHidden = (set < 3);
		cube_r9.isHidden = (set < 3);
		cube_r10.isHidden = (set < 2);
		cube_r4.isHidden = (set < 3);
		cube_r1.isHidden = (set < 2);
		cube_r2.isHidden = (set < 2);
		cube_r3.isHidden = (set < 2);
		r5_2.isHidden = (set < 2);
		cube_r21_2.isHidden = (set < 2);
		cube_r24_2.isHidden = (set < 2);
		cube_r21_3.isHidden = (set < 3);
		cube_r24_3.isHidden = (set < 3);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0f;
			GL11.glPushMatrix();
			GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
			GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
			bipedHead.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
			bipedBody.render(f5);
			bipedRightArm.render(f5);
			bipedLeftArm.render(f5);
			bipedRightLeg.render(f5);
			bipedLeftLeg.render(f5);
			bipedHeadwear.render(f5);
			GL11.glPopMatrix();
		} else {
			GL11.glPushMatrix();
			GL11.glScalef(1.01f, 1.01f, 1.01f);
			bipedHead.render(f5);
			GL11.glPopMatrix();
			bipedBody.render(f5);
			bipedRightArm.render(f5);
			bipedLeftArm.render(f5);
			bipedRightLeg.render(f5);
			bipedLeftLeg.render(f5);
			bipedHeadwear.render(f5);
		}
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	static {
		ModelIchoriumFortressArmor.hasSet = new HashMap<Integer, Integer>();
	}
}