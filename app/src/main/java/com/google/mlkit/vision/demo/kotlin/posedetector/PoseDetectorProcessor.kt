

package com.google.mlkit.vision.demo.kotlin.posedetector

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.demo.GraphicOverlay
import com.google.mlkit.vision.demo.java.posedetector.classification.PoseClassifierProcessor
import com.google.mlkit.vision.demo.kotlin.VisionProcessorBase
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase
import java.util.ArrayList
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/** A processor to run pose detector.  */
class PoseDetectorProcessor(
  private val context: Context,
  options: PoseDetectorOptionsBase,
  private val showInFrameLikelihood: Boolean,
  private val visualizeZ: Boolean,
  private val rescaleZForVisualization: Boolean,
  private val runClassification: Boolean,
  private val isStreamMode: Boolean
) : VisionProcessorBase<PoseDetectorProcessor.PoseWithClassification>(context) {

  private val detector: PoseDetector
  private val classificationExecutor: Executor

  private var poseClassifierProcessor: PoseClassifierProcessor? = null

  /**
   * Internal class to hold Pose and classification results.
   */
  class PoseWithClassification(val pose: Pose, val classificationResult: List<String>)
  init {
    detector = PoseDetection.getClient(options)
    classificationExecutor = Executors.newSingleThreadExecutor()
  }

  override fun stop() {
    super.stop()
    detector.close()
  }

  override
  fun detectInImage(image: InputImage): Task<PoseWithClassification> {
    return detector
      .process(image)
      .continueWith(
        classificationExecutor,
        { task ->
          val pose = task.getResult()
          var classificationResult: List<String> = ArrayList()
          if (runClassification) {
            if (poseClassifierProcessor == null) {
              poseClassifierProcessor = PoseClassifierProcessor(context, isStreamMode)
            }
            classificationResult = poseClassifierProcessor!!.getPoseResult(pose)
          }
          PoseWithClassification(pose, classificationResult)
        }
      )
  }

  override fun onSuccess(
    poseWithClassification: PoseWithClassification,
    graphicOverlay: GraphicOverlay
  ) {
    graphicOverlay.add(
      PoseGraphic(
        graphicOverlay, poseWithClassification.pose, showInFrameLikelihood, visualizeZ,
        rescaleZForVisualization, poseWithClassification.classificationResult
      )
    )
  }

  override fun onFailure(e: Exception) {
    Log.e(TAG, "Pose detection failed!", e)
  }

  companion object {
    private val TAG = "PoseDetectorProcessor"
  }
}
