package net.cazzar.mods.rfsolars.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.cazzar.mods.rfsolars.Config;
import net.cazzar.mods.rfsolars.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

public class TileSolarBase extends TileEntity implements IEnergyHandler {

	public static Random random = new Random();
	private EnergyStorage storage = new EnergyStorage(1000);
	public int damage;

	public TileSolarBase() {
		damage = 0;
        storage.setMaxTransfer(3200);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        if (!canInterface(from)) return 0;
        return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        if (!canInterface(from)) return 0;

        return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return from != ForgeDirection.UP;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 1000;
	}

	@Override
	public void updateEntity() {
		transferEnergy();
		if (canGenerate()) {
			generate();
			damagePanel();
		}
	}

	public boolean canGenerate() {
		return !(worldObj.provider.hasNoSky || worldObj.isRaining() || worldObj.isThundering() || !worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) || !worldObj.isDaytime() || damage >= 500);
	}

	public void damagePanel() {
		if (canGenerate() && random.nextInt(10) == 0)
			damage++;
		else if (worldObj.isRaining() || worldObj.isThundering() && random.nextInt(50) == 0 && damage >= 250)
			damage -= 250;
	}

	public void transferEnergy() {
		ForgeDirection direction = ForgeDirection.getOrientation(getBlockMetadata() % 6);
		TileEntity tile = worldObj.getBlockTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
		if (tile instanceof IEnergyHandler && !(tile instanceof TileSolarBase) && canGenerate()) {
			IEnergyHandler energyHandler = (IEnergyHandler) tile;
			storage.extractEnergy(energyHandler.receiveEnergy(direction.getOpposite(), storage.getEnergyStored() > 500 ? 500 : storage.getEnergyStored(), false), false);
		}
	}
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		damage = tagCompound.getInteger("damage");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("damage", damage);
	}

    /** This is the method you need to override to add different gen values. Don't touch anything else. Period. :D **/
    public int getGenerationValue() {
        return Config.baseGen;
    }

    public double getGenerationFactor() {
        long x = worldObj.getWorldInfo().getWorldTime();

        return MathHelper.clamp(Math.sin((2 * Math.PI) / 24000d * x), 1, 0);
    }

    public void generate() {
        storage.receiveEnergy((int) Math.round(getGenerationValue() * getGenerationFactor()), false);
    }
}
