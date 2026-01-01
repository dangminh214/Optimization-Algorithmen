import { Rectangle, PackingResult } from "../types";
import {
  BinPackingSolver,
  AreaBasedSelection,
  WidthBasedSelection,
  HeightBasedSelection,
} from "./binPackingProblem";

/**
 * Legacy GreedyPacker class - now uses generic algorithm implementation
 *
 * This class maintains the same interface but now delegates to the generic
 * greedy algorithm implementation, demonstrating proper separation of concerns.
 */
export class GreedyPacker {
  private solver: BinPackingSolver;

  constructor(boxSize: number) {
    // Use area-based selection strategy (First Fit Decreasing)
    this.solver = new BinPackingSolver(boxSize, new AreaBasedSelection());
  }

  pack(rectangles: Rectangle[]): PackingResult {
    return this.solver.solve(rectangles);
  }
}

/**
 * Alternative greedy packers with different selection strategies
 */
export class WidthBasedGreedyPacker {
  private solver: BinPackingSolver;

  constructor(boxSize: number) {
    this.solver = new BinPackingSolver(boxSize, new WidthBasedSelection());
  }

  pack(rectangles: Rectangle[]): PackingResult {
    return this.solver.solve(rectangles);
  }
}

export class HeightBasedGreedyPacker {
  private solver: BinPackingSolver;

  constructor(boxSize: number) {
    this.solver = new BinPackingSolver(boxSize, new HeightBasedSelection());
  }

  pack(rectangles: Rectangle[]): PackingResult {
    return this.solver.solve(rectangles);
  }
}
