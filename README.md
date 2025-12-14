# Optimization Algorithmen 

## 🎯 Project Overview
This project implements and compares **greedy** and **local search** algorithms for solving the **2D rectangle packing problem**. Given a set of rectangles with varying dimensions (rotatable by 90°) and a fixed container box size, the objective is to **minimize the number of boxes** required to pack all rectangles.

---

## 📊 Problem Description

### **Input Parameters:**
- **Rectangles**: Set of `n` rectangles with dimensions `(widthᵢ, heightᵢ)`
- **Rotation**: Each rectangle can be rotated 90° (width ↔ height)
- **Box Size**: Square container with fixed edge length `L`
- **Constraints**: No overlap between rectangles, all rectangles must be fully contained within boxes

### **Objective Function:**
- Minimize: Number of boxes used
- Subject to: All rectangles must be packed without overlap