package net.cazzar.mods.rfsolars.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.cazzar.mods.rfsolars.Config;
import net.cazzar.mods.rfsolars.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileSolarBase extends TileEntity implements IEnergyHandler {
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
        damagePanel();

        if (canGenerate()) generate();
	}

	public boolean canGenerate() {
		return !(worldObj.provider.hasNoSky || worldObj.isRaining() || worldObj.isThundering() || !worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) || !worldObj.isDaytime() || damage >= getMaxDamage());
	}

	public void damagePanel() {
		if (canGenerate() && worldObj.rand.nextInt(100) == 0) incrementDamage(1);

        if ((worldObj.isRaining() || worldObj.isThundering()) && worldObj.rand.nextInt(500) == 0) incrementDamage(5);
	}

    void incrementDamage(int amount) {
        if (damage + amount >= getMaxDamage()) {
            damage = getMaxDamage();
            return;
        }

        damage += amount;
    }

    int getMaxDamage() {
        return 500;
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

		return MathHelper.clamp(1.5D * Math.sin((2 * Math.PI) / 24000d * x), 1, 0);
	}

	public void generate() {
		storage.receiveEnergy((int) Math.round(getGenerationValue() * getGenerationFactor()), false);
	}
}
