package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.besinkitab.Besin
import com.example.besinkitab.ListeDirections
import com.example.besinkitab.databinding.BesinrecyclerviewrowBinding
import util.gorselIndir
import util.placeHolderYap

class BesinRecyclerAdapter(val besinlistesi: ArrayList<Besin>):RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {

    class BesinViewHolder(val binding:BesinrecyclerviewrowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
       val binding =BesinrecyclerviewrowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BesinViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return besinlistesi.size
    }

    fun besinListesiguncelle(yenibesinlistesi:List<Besin>){
        besinlistesi.clear()
        besinlistesi.addAll(yenibesinlistesi)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
       holder.binding.besinSim.text=besinlistesi[position].besinİsim
        holder.binding.besinKalori.text=besinlistesi[position].besinKalori

        holder.itemView.setOnClickListener{
            val action=ListeDirections.actionListeToDetay(besinlistesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageviewrow.gorselIndir(besinlistesi[position].besinGorsel,
            placeHolderYap(holder.itemView.context)
        )

    }
}