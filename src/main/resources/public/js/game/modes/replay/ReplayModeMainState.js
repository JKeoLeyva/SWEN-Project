/**
 * This module exports the ReplayModeStartState class constructor.
 * 
 * This component is an concrete implementation of a state
 * for the Game view; this state represents the starting state
 * of the Replay mode.
 * 
 * This is an exercise for the student.
 */
define(function(require){
  'use strict';
  
  // imports
  var ReplayModeConstants = require('./ReplayModeConstants');

  /**
   * Constructor function.
   * 
   * @param {ReplayController} controller
   *    The Replay mode controller object.
   */
  function ReplayModeMainState(controller) {
    // private attributes
    this._controller = controller;
  }

  //
  // Public (external) methods
  //

  /**
   * Get the symbolic name this state.
   */
  ReplayModeMainState.prototype.getName = function getName() {
    return ReplayModeConstants.REPLAY_MODE_MAIN_STATE;
  };
  
  /**
   * Method when entering this state.
   * 
   * Build and begin the Replay view mode.
   */
  ReplayModeMainState.prototype.onEntry = function onEntry() {
    this._controller.disableAllPieces();

    if(getParam('move') !== '0')
      this._controller.button(ReplayModeConstants.PREVIOUS_BUTTON_ID, true);
  };

  /**
   * Find the value of a GET parameter
   *
   * @param name the GET parameter name
   */
  function getParam(name) {
    const result = window.location.search
      .substr(1)
      .split('&')
      .map(param => param.split("="))
      .find(param => param[0] === name);

    return result ? result[1] : result;
  }

  /**
   * Go back one move
   */
  ReplayModeMainState.prototype.previousMove = function() {
    const move = parseInt(getParam('move'), 10) - 1;
    window.location = '/replay?id=' + getParam('id') + '&move=' + move;
  };

  /**
   * Go forward one move
   */
  ReplayModeMainState.prototype.nextMove = function() {
    const move = parseInt(getParam('move'), 10) + 1;
    window.location = '/replay?id=' + getParam('id') + '&move=' + move;
  };
  
  // export class constructor
  return ReplayModeMainState;
});
