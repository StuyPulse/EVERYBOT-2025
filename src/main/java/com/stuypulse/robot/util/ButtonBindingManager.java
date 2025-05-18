package com.stuypulse.robot.util;

import java.nio.channels.NotYetBoundException;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;

/**
 * The {@code ButtonBindingManager} is a simple manager to store your button
 * bindings for a certain controller.
 * Useful for storing button bindings for multiple controllers, or to quickly
 * switch between users who prefer different button bindings during testing.
 * <p>
 * 
 * <b>Add profiles using the {@code .add()} method:</b>
 * 
 * <blockquote><pre>
 *     ButtonBindingManager driverButtonBindings
 * 
 *driverButtonBindings.add(ProfileOneName, this::configureGamepadBindingsProfileOne);
 *driverButtonBindings.add(ProfileTwoName, this::configureGamepadBindingsProfileTwo);
 * </pre></blockquote>
 * 
 * <b>And then configure those button bindings onto a gamepad using the {@code .configureButtonBindings()} method:</b>
 * <blockquote><pre>
 *Gamepad gamepadOne = configureButtonBindings(gamepadPortOne, ProfileOneName);
 *Gamepad gamepadTwo = configureButtonBindings(gamepadPortTwo, ProfileOneName);
 * </pre></blockquote>
 * 
 * @author Daniel Manita
 * @version 1.0
 * @since May 18th, 2025
 */
public class ButtonBindingManager {
    private Map<String, Function<AutoGamepad, AutoGamepad>> profiles;

    /**
     * Adds a profile that is then available to bind a gamepad to, which is identifiable by the profile name. 
     * @param profileName - The ID of the profile
     * @param profileExecute - The function that binds the bindings for that profile. Requires the unbound gamepad.
     */
    public void addProfile(String profileName, Function<AutoGamepad, AutoGamepad> profileExecute) {
        profiles.putIfAbsent(profileName, profileExecute);
    }

    /**
     * 
     * @param gamepadPort - The port ID of the gamepad
     * @param profileName - The ID of the profile that is being bound to the gamepad
     * @return The configured gamepad object, in case you want to modify it manually somehow
     */
    public Gamepad configureButtonBindings(int gamepadPort, String profileName) {
        AutoGamepad gamepad = new AutoGamepad(gamepadPort);

        if (profiles.isEmpty()) {
            throw new IllegalArgumentException(
                    "No profiles added to ButtonBindingManager! Add profiles using the .add() command.");
        }

        if (!profiles.containsKey(profileName)) {
            throw new IllegalArgumentException(
                    "No such profile detected in ButtonBindingManager! Add profiles using the .add() command.");
        }

        return profiles.get(profileName).apply(gamepad);
    }
}
