/**
 * This module exports the ReplayController class constructor.
 * 
 * This component manages the Replay mode behavior of the GameView
 */
define(function(require){
  'use strict';
  
  // imports
  const StatePatternMixin = require('../../util/StatePatternMixin');
  const ControlsToolbarMixin = require('../../util/ControlsToolbarMixin');
  const ReplayModeConstants = require('./ReplayModeConstants');

  // import REPLAY mode states
  const ReplayModeMainState = require('./ReplayModeMainState');

  /**
   * Constructor function.
   */
  function ReplayController(boardController, totalMoves) {
    this._boardController = boardController;
    this._totalMoves = totalMoves;

    // Add the StatePattern mixin
    StatePatternMixin.call(this);
    // create states and a lookup map
    this.addStateDefinition(ReplayModeConstants.REPLAY_MODE_MAIN_STATE, new ReplayModeMainState(this));
    
    // Add the ModeControls mixin
    ControlsToolbarMixin.call(this);
    this.addButton(
      ReplayModeConstants.PREVIOUS_BUTTON_ID,
      'Previous',
      false,
      ReplayModeConstants.PREVIOUS_BUTTON_TOOLTIP,
      this.previousMove
    );
    this.addButton(
      ReplayModeConstants.NEXT_BUTTON_ID,
      'Next',
      true,
      ReplayModeConstants.NEXT_BUTTON_TOOLTIP,
      this.nextMove
    );

    // Public (internal) methods

    /**
     * Start Replay mode.
     */
    this.startup = function startup() {
      // start Replay mode
      this.setState(ReplayModeConstants.REPLAY_MODE_MAIN_STATE);
    };
  }

  /**
   * Go back one move
   */
  ReplayController.prototype.previousMove = function() {
    this._delegateStateMessage('previousMove', arguments);
  };

  /**
   * Go forward one move
   */
  ReplayController.prototype.nextMove = function() {
    this._delegateStateMessage('nextMove', arguments);
  };

  ReplayController.prototype.disableAllPieces = function() {
    this._boardController.disableAllMyPieces();
  };

  ReplayController.prototype.button = function(id, on) {
    if(on)
      this.enableButton(id);
    else
      this.disableButton(id);
  };

  ReplayController.prototype.totalMoves = function() {
    return this._totalMoves;
  };

  ReplayController.prototype.setNextToFinish = function() {
    console.log(this);
    this.setText(ReplayModeConstants.NEXT_BUTTON_ID, "Finish");
  };

  //
  // Public (external) methods
  //

  // export class constructor
  return ReplayController;
  
});
