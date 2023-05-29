package nine.teta.colormixer.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import nine.teta.colormixer.R;
import nine.teta.colormixer.adapter.FavouriteColorItemModel;
import nine.teta.colormixer.setup.LightningRays;
import nine.teta.colormixer.setup.MyRand;

public class ColorCanvasFragment extends Fragment {

    private FavouriteColorFragmentViewModel viewModel;
    private CanvasView canvasView;
    private List<FavouriteColorItemModel> allColors;

    private MyRand mr;
    private LinearLayout canvasContainer;

    public ColorCanvasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(FavouriteColorFragmentViewModel.class);
        mr = new MyRand();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        allColors = viewModel.getAllColors().getValue();
        return inflater.inflate(R.layout.fragment_color_canvas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        canvasContainer = (LinearLayout)view.findViewById(R.id.canvasContainer);
        canvasView = new CanvasView(view.getContext());
        canvasContainer.addView(canvasView);
        Button clearButton = (Button)view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener((v) -> {
            canvasView.clear();
        });

    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(mr.rancol(allColors));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(4);
        return paint;
    }

    private Paint getPaint(int notThisColor){
        Paint paint = getPaint();
        paint.setColor(mr.rancolButnotthis(allColors, notThisColor));
        return paint;
    }




    class CanvasView extends View{

        private Bitmap canvasBitmap;
        private Canvas viewCanvas;
        private boolean isCanvasSetuped = false;
        private int height;
        private int width;
        private boolean triblock = false;
        private int touchX;
        private int touchY;
        private int backgroundColor;

        public CanvasView(Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            width = w;
            height = h;
            clear();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (touchX != 0 || touchY != 0) drawThis(viewCanvas);
            canvas.drawBitmap(canvasBitmap, 0,0,null);
        }

        private void drawThis(Canvas canvas) {
            LightningRays lightningRays = new LightningRays(width, height);
            Paint paint = getPaint(backgroundColor);
            for (List<int[]> arrayPath : lightningRays.getRandRays(touchX, touchY)){
                int[] firstPoint = arrayPath.get(0);
                for (int i = 1; i < arrayPath.size(); i++) {
                    int[] secondPoint = arrayPath.get(i);
                    canvas.drawLine(firstPoint[0], firstPoint[1], secondPoint[0], secondPoint[1], paint);
                    firstPoint = secondPoint;
                }
            }
            touchX = 0;
            touchY = 0;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && !triblock){
                touchX = (int) event.getX();
                touchY = (int) event.getY();
                triblock = true;
                invalidate();



            } else if (event.getAction() == MotionEvent.ACTION_MOVE){

            } else if (event.getAction() == MotionEvent.ACTION_UP){
                triblock = false;
            }
            return true;
        }

        public void clear() {
            canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            viewCanvas = new Canvas(canvasBitmap);
            backgroundColor = mr.rancol(allColors);
            viewCanvas.drawColor(backgroundColor);
            invalidate();
        }
    }


}