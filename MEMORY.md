# Memory & Performance Specification (MEMORY) — SubTracker

## 1. Heap Allocation & Lifecycle Targets
- **Peak Process Heap Limit**: Under 35 MB RAM during active dashboard navigation.
- **Background Worker Footprint**: Under 15 MB RAM when performing scheduled notification calculations.

---

## 2. Resource Management Strategy
- **Image & Icon Caching**: Template service logos (Netflix, Spotify, etc.) stored as local vector drawables (`.xml`) rather than uncompressed raster bitmaps (`.png`), keeping asset memory overhead under 1 MB.
- **Database Query Paging**: Historical billing logs loaded via Android Paging 3 library with a page size of 20 items to prevent unbounded list memory consumption.
- **Leak Prevention**: Coroutine scopes tied strictly to `viewModelScope` and `rememberCoroutineScope()`, guaranteeing zero detached background thread leaks upon activity destruction.
