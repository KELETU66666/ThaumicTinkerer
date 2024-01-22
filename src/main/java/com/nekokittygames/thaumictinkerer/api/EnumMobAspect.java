package com.nekokittygames.thaumictinkerer.api;

import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityFireBat;
import thaumcraft.common.entities.monster.EntityWisp;

import java.util.Map;

public enum EnumMobAspect {

    SnowMan(EntitySnowman.class, new Aspect[]{Aspect.WATER, Aspect.WATER, Aspect.MAN}),
    Bat(EntityBat.class, new Aspect[]{Aspect.AIR, Aspect.AIR, Aspect.FLIGHT}, 1.9f, -0.3f),
    Blaze(EntityBlaze.class, new Aspect[]{Aspect.FIRE, Aspect.FIRE, Aspect.FIRE}),
    BrainyZombie(EntityBrainyZombie.class, new Aspect[]{Aspect.MAGIC, Aspect.UNDEAD, Aspect.DESIRE}, "Thaumcraft."),
    Firebat(EntityFireBat.class, new Aspect[]{Aspect.FLIGHT, Aspect.FIRE, Aspect.MAGIC}, 1.9f, -0.3f),
    CaveSpider(EntityCaveSpider.class, new Aspect[]{Aspect.BEAST, Aspect.DEATH, Aspect.DEATH}),
    Chicken(EntityChicken.class, new Aspect[]{Aspect.PLANT, Aspect.FLIGHT, Aspect.BEAST}),
    Cow(EntityCow.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.BEAST}),
    Creeper(EntityCreeper.class, new Aspect[]{Aspect.MAGIC, Aspect.BEAST, Aspect.ELDRITCH}),
    Enderman(EntityEnderman.class, new Aspect[]{Aspect.ELDRITCH, Aspect.ELDRITCH, Aspect.MAN}, 0.3f, 0.0f),
    Ghast(EntityGhast.class, new Aspect[]{Aspect.FIRE, Aspect.FLIGHT, Aspect.FLIGHT}, 0.1f, 0.2f),
    EntityHorse(EntityHorse.class, new Aspect[]{Aspect.BEAST, Aspect.BEAST, Aspect.MOTION}),
    VillagerGolem(EntityIronGolem.class, new Aspect[]{Aspect.METAL, Aspect.METAL, Aspect.MAN}, 0.3f, 0.0f),
    LavaSlime(EntityMagmaCube.class, new Aspect[]{Aspect.FIRE, Aspect.ALCHEMY, Aspect.ALCHEMY}, 0.6f, 0.0f) {
        @Override
        protected Entity createEntity(World worldObj) {
            return setSlimeSize(super.createEntity(worldObj), 1);
        }
    },
    MushroomCow(EntityMooshroom.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.PLANT}),
    Ozelot(EntityOcelot.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.ELDRITCH}),
    Pig(EntityPig.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.MOTION}),
    PigZombie(EntityPigZombie.class, new Aspect[]{Aspect.UNDEAD, Aspect.DESIRE, Aspect.FIRE}),
    Sheep(EntitySheep.class, new Aspect[]{Aspect.EARTH, Aspect.CRAFT, Aspect.BEAST}),
    Silverfish(EntitySilverfish.class, new Aspect[]{Aspect.METAL, Aspect.METAL, Aspect.EARTH}),
    Skeleton(EntitySkeleton.class, new Aspect[]{Aspect.UNDEAD, Aspect.MAN, Aspect.UNDEAD}),
    Slime(EntitySlime.class, new Aspect[]{Aspect.ALCHEMY, Aspect.ALCHEMY, Aspect.BEAST}, 0.6f, 0.0f) {
        @Override
        protected Entity createEntity(World worldObj) {
            return setSlimeSize(super.createEntity(worldObj), 1);
        }
    },
    Spider(EntitySpider.class, new Aspect[]{Aspect.BEAST, Aspect.UNDEAD, Aspect.UNDEAD}),
    Squid(EntitySquid.class, new Aspect[]{Aspect.WATER, Aspect.WATER, Aspect.WATER}, 0.3f, 0.5f),
    Villager(EntityVillager.class, new Aspect[]{Aspect.MAN, Aspect.MAN, Aspect.MAN}),
    Wisp(EntityWisp.class, new Aspect[]{Aspect.AIR, Aspect.MAGIC, Aspect.MAGIC}, "Thaumcraft."),
    Witch(EntityWitch.class, new Aspect[]{Aspect.MAGIC, Aspect.UNDEAD, Aspect.ELDRITCH}, 0.35f, 0.0f),
    Wolf(EntityWolf.class, new Aspect[]{Aspect.BEAST, Aspect.BEAST, Aspect.BEAST}),
    Zombie(EntityZombie.class, new Aspect[]{Aspect.DESIRE, Aspect.DESIRE, Aspect.UNDEAD});

    public static Map<EnumMobAspect, Entity> entityCache = Maps.newHashMap();
    public Aspect[] aspects;
    public Class entity;
    public String prefix;
    private float scale;
    private float offset;
    EnumMobAspect(Class entity, Aspect[] aspects, float scale, float offset) {
        this.aspects = aspects;
        this.entity = entity;
        this.scale = scale;
        this.offset = offset;
    }
    EnumMobAspect(Class entity, Aspect[] aspects, float scale, float offset, String prefix) {
        this(entity, aspects, scale, offset);
        this.prefix = prefix;
    }

    EnumMobAspect(Class entity, Aspect[] aspects) {
        this.aspects = aspects;
        this.entity = entity;
        this.scale = 1.1f;
        this.offset = 0.0f;
    }

    EnumMobAspect(Class entity, Aspect[] aspects, String prefix) {
        this(entity, aspects);
        this.prefix = prefix;
    }

    public static Entity getEntityFromCache(EnumMobAspect ent, World worldObj) {
        Entity entity = entityCache.get(ent);
        if (entity == null) {
            entity = ent.createEntity(worldObj);
            entityCache.put(ent, entity);
        }
        return entity;
    }

    private static Entity setSlimeSize(Entity entity, int size) {

        if (entity instanceof EntitySlime) {
            ((EntitySlime) entity).setSlimeSize(size, true);
        }

        return entity;
    }

    public static Aspect[] getAspectsForEntity(Entity e) {
        return getAspectsForEntity(e.getClass());
    }

    public static Aspect[] getAspectsForEntity(Class clazz) {
        for (EnumMobAspect e : EnumMobAspect.values()) {
            if (e.entity.isAssignableFrom(clazz)) {
                return e.aspects;
            }
        }
        return null;
    }

    public float getVerticalOffset() {
        return offset;
    }

    public float getScale() {
        return scale;
    }

    public Class getEntityClass() {
        return entity;
    }

    public Entity getEntity(World worldObj) {
        return getEntityFromCache(this, worldObj);
    }

    protected Entity createEntity(World worldObj) {
        return EntityList.createEntityByIDFromName(new ResourceLocation(toString()), worldObj);
    }
    @Override
    public String toString() {
        return prefix == null ? super.toString() : prefix + super.toString();
    }
}