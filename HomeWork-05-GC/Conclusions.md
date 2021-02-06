GCCycles     GC  appTime(s) stopTime(s)  Heap(mb)     maxGCPause(s)
5905         G1  63         53.67           256          default
5888         G1  64         57.67           256          100
5800         G1  61         53.85           256          0.01
1207         G1  59         17.91           2048         default
567          G1  62         9.92            4096         default
152          G1  58         2.05            8192         default
150          G1  60         2.14            8192         100
164          G1  57         2.07            8192         0.01
48           G1  79         0.45            16384        default

G1:
1) GC cycles go down with Heap increase.
2) AppTime stays about the same, but @16384mb heap it goes significantly higher.
3) App stopped time due to GC goes down with bigger heap.
My pick -> G1 (@8192mb, 0.01s) works faster and stopTime is low.

GCCycles     GC  appTime(s) stopTime(s)  Heap(mb)     maxGCPause(s)
5288         ||  60         4.06         256          100
5283         ||  62         4.43         256          0.01
310          ||  56         0.28         4096         100
310          ||  55         0.27         4096         0.01
155          ||  56         0.16         8192         100
155          ||  56         0.18         8192         default
155          ||  57         0.17         8192         0.01
78           ||  59         0.12         16384        default

Parallel Collector:
1) GC cycles go down with Heap increase.
2) AppTime stays about the same. @256mb & @16384mb AppTime is ~5s higher.
3) App stopped time due to GC stays low with 4096mb and more.
My pick -> ||GC (@4096mb, 0.01s) works faster and stopTime is low.

GCCycles     GC  appTime(s) stopTime(s)  Heap(mb)     maxGCPause(s)
2831         ZGC 132        72.32        256          default
631          ZGC 63         4.98         1024         default
572          ZGC 59         0.0          2048         default
204          ZGC 59         0.0          4096         default
73           ZGC 60         0.0          8192         default
41           ZGC 280        0.0          16384        default

ZGC:
1) GC cycles go down with Heap increase.
2) AppTime with @1024mb-8192mb heap stays about the same, quite low.
   Below 1024mb and more than 8192mb the appTime goes significantly up.
3) App stopped time due to GC does not stop the app, but sacrifices appTime. @16gb and more
   it extremely increases appTime. @1gb and lower stopTime starts to show up quite bad.
My pick -> ZGC (@2048mb, default) works fast and does not slow the app.

Result:
For our specific case I would go with Parallel Garbage Collector
    with 2096mb heap with default maxGCPause.