/**
 * This module exports a map of constants used in the REPLAY mode.
 */
define(function(){
    'use strict';

    /**
     * This module is a map of constant symbols to their names.
     * Used in methods to change GameView Replay mode states.
     */
    return {
      // States
      REPLAY_MODE_MAIN_STATE: 'REPLAY_MODE_MAIN_STATE',

      // Buttons
      PREVIOUS_BUTTON_ID: 'previousButton',
      PREVIOUS_BUTTON_TOOLTIP: 'Go back one move',
      NEXT_BUTTON_ID: 'nextButton',
      NEXT_BUTTON_TOOLTIP: 'Go forward one move'
    };
});