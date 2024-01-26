package com.nekokittygames.thaumictinkerer.common.blocks.Kami;

import com.nekokittygames.thaumictinkerer.common.blocks.TTBlock;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.client.fx.FXDispatcher;

import java.util.ArrayList;
import java.util.Random;

public class BlockChlorophyteOre extends TTBlock {

    public BlockChlorophyteOre() {
        super(LibBlockNames.CHLOROPHYTE_ORE, Material.ROCK);
        setHardness(3.0F);
        setResistance(10.0F);
        setLightLevel(5);
        setTickRandomly(true);
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        double rand1 = random.nextDouble();
        if(rand1 * rand1 * rand1 * 256 > pos.getY())
            return;
        int[] range = new int[]{-1, 0, 1};
        int flag = 0;
        ArrayList<int[]> poses = new ArrayList<>();

        for(int i : range)
            for(int j : range)
                for(int k : range) {
                    poses.add(new int[]{i, j, k});
                    Block block1 = world.getBlockState(shiftBlockPos(pos,i,j,k)).getBlock();
                    Block block2 = world.getBlockState(shiftBlockPos(pos,-i,-j,-k)).getBlock();
                    if(i*i+j*j+k*k==0)continue;
                    if(isEqualTo(block1, Blocks.WATER) && !isEqualTo(block2,Blocks.LAVA))
                        flag = flag | 1;
                    if(isEqualTo(block1, Blocks.LAVA) && !isEqualTo(block2,Blocks.WATER))
                        flag = flag | 2;
                    if(isEqualTo(block1,this)&&isEqualTo(block2,this))
                        return;
                }
        if(flag!=3)return;
        int time = (int) (world.getWorldTime()%24000);
        if(time>11500||time<500)return;
        int angle = (int) (180.0* time / 12000);
        int x_y = (int) Math.tan( 90 - angle );
        for (int dy = 10; dy < 2560; dy++){
            int dx = x_y * dy;
            if(pos.getY() + 0.1*dy > 255) break;
            BlockPos pos2 = shiftBlockPos(pos, (int) (0.1*dx), (int) (0.1*dy), 0);
            if(world.isAirBlock(pos2))continue;
            IBlockState state1 = world.getBlockState(pos2);
            if(state1 == null)continue;
            if(state1.isTranslucent())continue;
            return;
        }
        ArrayList<BlockPos> list  = new ArrayList<>();
        list.add(pos);
        int n = 0;
        while(n<90){
            if(n>= list.size())break;
            for (int[] i : poses){
                BlockPos pos2 = shiftBlockPos(list.get(n),i[0],i[1],i[2]);
                if(list.contains(pos2))continue;
                if(isEqualTo(world.getBlockState(pos2).getBlock(),this)){
                    list.add(pos2);
                }
            }
            n+=1;
        }
        if(random.nextDouble()> Math.sin(0.0+n))return;
        //Convertion
        int[] rp = poses.get(world.rand.nextInt(poses.size())); //randomPos
        BlockPos pos2 = shiftBlockPos(pos,rp[0],rp[1],rp[2]);
        if(!isEqualTo(world.getBlockState(pos2).getBlock(),Blocks.MOSSY_COBBLESTONE))return;
        int light = world.getLight(pos);
        if(randDouble(3.0,13.0, world)<light)return;
        FXDispatcher.INSTANCE.sparkle(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 1F, 4, random.nextFloat() / 2);
        if(!isEqualTo(world.getBlockState(pos2).getBlock(),Blocks.MOSSY_COBBLESTONE))return;
        world.setBlockState(pos2, this.getDefaultState());
    }

    private BlockPos shiftBlockPos(BlockPos a, int x, int y,  int z){
        return new BlockPos(a.getX() +x, a.getY() +y, a.getZ() +z);
    }

    private double randDoubleRaw(World world){
        if(world == null){
            return Math.random();
        }
        else{
            return world.rand.nextDouble();
        }
    }

    private double randDouble(double max, double min, World world){
        return randDoubleRaw(world)*(max-min)+min;
    }
}
