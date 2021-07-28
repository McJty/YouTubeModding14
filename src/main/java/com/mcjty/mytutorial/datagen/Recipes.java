package com.mcjty.mytutorial.datagen;

import com.mcjty.mytutorial.setup.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(Registration.FIRSTBLOCK.get())
                .pattern("xxx")
                .pattern("x#x")
                .pattern("xxx")
                .define('x', Tags.Items.COBBLESTONE)
                .define('#', Tags.Items.DYES_RED)
                .group("mytutorial")
                .unlockedBy("cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);
    }
}
