package ch.bailu.aat.activities;

import android.content.Intent;
import android.widget.LinearLayout;
import ch.bailu.aat.description.AverageSpeedDescription;
import ch.bailu.aat.description.ContentDescription;
import ch.bailu.aat.description.DateDescription;
import ch.bailu.aat.description.DistanceDescription;
import ch.bailu.aat.description.MaximumSpeedDescription;
import ch.bailu.aat.description.NameDescription;
import ch.bailu.aat.description.TimeDescription;
import ch.bailu.aat.helpers.AppLayout;
import ch.bailu.aat.services.directory.DirectoryServiceHelper;
import ch.bailu.aat.services.directory.DynamicDirectoryServiceHelper;
import ch.bailu.aat.views.ControlBar;
import ch.bailu.aat.views.DateFilterView;
import ch.bailu.aat.views.GpxListSummaryView;

public class TrackListActivity extends AbsGpxListActivity {

    private GpxListSummaryView summaryView;


    @Override
    public void createHeader(ControlBar bar) {
        bar.addViewIgnoreSize(new DateFilterView(this));
    }


    @Override
    public void createSummaryView(LinearLayout layout) {
        ContentDescription data[] = new ContentDescription[] {
                new MaximumSpeedDescription(this),
                new DistanceDescription(this),
                new AverageSpeedDescription(this),
                new TimeDescription(this)
        };   

        summaryView = new GpxListSummaryView(getServiceContext(),  data);
        
        layout.addView(summaryView, AppLayout.getScreenSmallSide(this), AppLayout.getScreenSmallSide(this) /3);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public DirectoryServiceHelper createDirectoryServiceHelper() {
        return new DynamicDirectoryServiceHelper(getServiceContext());
    }

    @Override
    public ContentDescription[] getGpxListItemData() {
        return new ContentDescription[] {
                new DateDescription(this),
                new DistanceDescription(this),
                new AverageSpeedDescription(this),
                new TimeDescription(this),
                new NameDescription(this)
        };    		
    }

    @Override
    public void displayFile() {
        Intent intent=new Intent(this,FileContentActivity.class);
        startActivity(intent);
    }
}
