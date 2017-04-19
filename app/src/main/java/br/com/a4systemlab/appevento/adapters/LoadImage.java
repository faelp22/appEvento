package br.com.a4systemlab.appevento.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import br.com.a4systemlab.appevento.helpers.ParticipanteViewHolder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

/**
 * Created by isael on 19/04/17.
 */
public class LoadImage extends AsyncTask<Integer, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private View view;
    private Context context;
    private String caminhoFoto;
    ParticipanteViewHolder holder;

    public LoadImage(View view, ParticipanteViewHolder holder, String caminhoFoto, Context context) {
        this.view = view;
        this.context = context;
        this.caminhoFoto = caminhoFoto;
        this.holder = holder;
        imageViewReference = new WeakReference<>(holder.campoFoto);
    }

    @Override
    protected Bitmap doInBackground(Integer... integers) {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(caminhoFoto), null, options);
            bm = bm.createScaledBitmap(bm, 100, 100, true);
            return bm;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }

    }
}
